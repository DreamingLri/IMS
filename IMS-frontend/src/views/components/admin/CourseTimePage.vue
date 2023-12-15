<script setup>

import {Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const courseList = ref([])

const addCourseDialog = ref(false)
const updateCourseDialog = ref(false)

const ruleForm = ref()
const addTimeForm = reactive({
  courseId: '',
  weekday: '',
  session: '',
})
const updateTimeForm = reactive({
  id: '',
  courseId: '',
  weekday: '',
  session: '',
})
const rules = reactive({

})

const closeAddDialog = () =>{
  addCourseDialog.value = false
  addTimeForm.weekday = ''
  addTimeForm.session = ''
}

const closeUpdateDialog = () =>{
  updateCourseDialog.value = false
}
const openUpdateDialog = (row) =>{
  updateCourseDialog.value = true

  updateTimeForm.id = row.id
  updateTimeForm.weekday = row.weekday
  updateTimeForm.session = row.session
  updateTimeForm.courseId = row.course.id
}

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/course/insertCourseTime' , addTimeForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('添加成功')
          closeAddDialog()
          reset()
        } else {
          ElMessage.error('添加失败')
          console.log(res.message)
          reset()
        }
      })
    } else {
      ElMessage.error('请检查填入的信息是否正确')
      console.log('error submit!', fields)
    }
  })
}

const updateForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/course/updateCourseTimeById', updateTimeForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('更新成功')
          closeUpdateDialog()
          reset()
        } else {
          ElMessage.error('更新失败')
          console.log(updateTimeForm)
          reset()
        }
      })
    } else {
      ElMessage.error('请检查填入的信息是否正确')
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}

const name = ref('')

const getCourseTimeList = () => {
  request.get("/course/listCourseWithTime?name="+name.value).then(res => {
    if(res.code === 200){
      courseList.value = res.data
      console.log(courseList.value)
    } else {
      ElMessage.error(res.message)
    }
  })
}


const courses = ref([])
const getCourseList = () =>{
  request.get("course/listCourse").then(res => {
    if(res.code === 200){
      courses.value = res.data;
    } else {
      ElMessage.error(res.message)
    }
  })
}

const reset = () =>{
  name.value = ''
  getCourseTimeList()
}

const removeCourse = (id) =>{
  request.delete("/course/deleteCourseTimeById/" + id).then(res => {
    if(res.code === 200){
      ElMessage.success("删除成功")
      reset()
    } else {
      ElMessage.error(res.message)
    }
  })
}


onMounted(() => {
  getCourseTimeList()
  getCourseList()
})
</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-input v-model="name" placeholder="请输入" class="input-with-select" style="height: 30px; width: 300px"></el-input>
        <el-button @click="getCourseTimeList" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
      </div>
      <div style="height: 15%; width: 100%"/>
      <div style="width: 100%; height: 50%; display: flex">

        <el-button @click="reset" type="danger" plain><el-icon style="margin-right: 3px"><Refresh /></el-icon>重置</el-button>
        <el-button type="success" plain @click="addCourseDialog = true"><el-icon style="margin-right: 3px"><Plus /></el-icon>新增</el-button>
      </div>
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="courseList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50"/>
        <el-table-column prop="course.name" label="课程名称" width="130" />
        <el-table-column prop="weekday" label="星期" width="100"/>
        <el-table-column prop="session" label="节次" width="100"/>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="openUpdateDialog(scope.row)"><el-icon style="margin-right: 5px"><Edit /></el-icon>修改</el-button>
            <el-popconfirm title="你确定要删除这个课程时间吗?" @confirm="removeCourse(scope.row.id)" confirm-button-text="确定" cancel-button-text="取消">
              <template #reference>
                <el-button type="danger" plain><el-icon style="margin-right: 5px"><Delete /></el-icon>删除</el-button>
              </template>
            </el-popconfirm>

          </template>
        </el-table-column>
      </el-table>
    </el-scrollbar>
  </div>

  <!--新建课程面板-->
  <el-dialog
      v-model="addCourseDialog"
      title="新增课程信息"
      width="30%"
      :before-close="closeAddDialog"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="addTimeForm" label-width="120px" :rules="rules" ref="ruleForm">
        <el-form-item label="课程">
          <el-select v-model="addTimeForm.courseId" class="m-2" placeholder="请选择课程">
            <el-option
                v-for="item in courses"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="星期">
          <el-select clearable v-model="addTimeForm.weekday" placeholder="请选择">
            <el-option v-for="item in ['星期一','星期二','星期三','星期四','星期五','星期六','星期日']" :value="item" :label="item"/>
          </el-select>
        </el-form-item>
        <el-form-item label="节次">
          <el-select clearable v-model="addTimeForm.session" placeholder="请选择">
            <el-option v-for="item in ['一','二','三','四','五','六','七','八','九','十','十一']" :value="item" :label="item"/>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleForm)">新建</el-button>
          <el-button @click="resetForm(ruleForm)">重置</el-button>
        </el-form-item>

      </el-form>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeAddDialog">取消</el-button>
      </span>
    </template>
  </el-dialog>

  <!--更新课程面板-->
  <el-dialog
      v-model="updateCourseDialog"
      title="更新课程信息"
      width="30%"
      :before-close="closeUpdateDialog"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="updateTimeForm" label-width="120px" :rules="rules" ref="ruleForm">

        <el-form-item label="星期">
          <el-select clearable v-model="updateTimeForm.weekday" placeholder="请选择">
            <el-option v-for="item in ['星期一','星期二','星期三','星期四','星期五','星期六','星期日']" :key="item" :value="item" :label="item"/>
          </el-select>
        </el-form-item>
        <el-form-item label="节次">
          <el-select clearable v-model="updateTimeForm.session" placeholder="请选择">
            <el-option v-for="item in ['一','二','三','四','五','六','七','八','九','十','十一']" :key="item" :value="item" :label="item"/>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="updateForm(ruleForm)">更新</el-button>
          <el-button @click="resetForm(ruleForm)">重置</el-button>
        </el-form-item>

      </el-form>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeUpdateDialog">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.main-wrapper{
  width: 100%;
  height: 100%;
  background-color: white;
  border-radius: 8px;
  box-sizing: border-box;
  padding: 20px;
}

.header-wrapper{
  width: 100%;
  height: 10%;
}
.table-wrapper{
  height: 85%;
  width: 100%;
}
</style>