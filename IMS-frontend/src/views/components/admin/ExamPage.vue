<script setup>
import {Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";


const userList = ref([])

const addExamDialog = ref(false)
const updateExamDialog = ref(false)

const ruleForm = ref()
const addExamForm = reactive({
  name: '',
  date: '',
  startTime: '',
  endTime: '',
  examPlace: '',
  examDuration: '',
  invigilator: '',
  courseId: '',
})
const updateExamForm = reactive({
  id: '',
  name: '',
  date: '',
  startTime: '',
  endTime: '',
  examPlace: '',
  examDuration: '',
  invigilator: '',
  courseId: '',
})

const rules = reactive({
  name: [
    { required: true, message: '考试名不能为空', trigger: 'blur' },
  ],
  date: [
    { required: true, message: '考试日期不能为空', trigger: 'blur' },
  ],
  startTime: [
    { required: true, message: '开始时间不能为空', trigger: 'blur' },
  ],
  endTime: [
    { required: true, message: '结束时间不能为空', trigger: 'blur' },
  ],
  examPlace: [
    { required: true, message: '考试地点不能为空', trigger: 'blur' },
  ],
  examDuration: [
    { required: true, message: '考试时长不能为空', trigger: 'blur' },
  ],
  invigilator: [
    { required: true, message: '监考老师不能为空', trigger: 'blur' },
  ],
})

const closeAddDialog = () =>{
  addExamDialog.value = false

  addExamForm.name = ''
  addExamForm.date = ''
  addExamForm.startTime = ''
  addExamForm.endTime = ''
  addExamForm.examPlace = ''
  addExamForm.examDuration = ''
  addExamForm.invigilator = ''
  addExamForm.courseId = ''
}

const closeUpdateDialog = () =>{
  updateExamDialog.value = false
}
const openUpdateDialog = (row) =>{
  updateExamDialog.value = true

  updateExamForm.id = row.id
  updateExamForm.name = row.name
  updateExamForm.date = row.date
  updateExamForm.startTime = row.startTime
  updateExamForm.endTime = row.endTime
  updateExamForm.examPlace = row.examPlace
  updateExamForm.examDuration = row.examDuration
  updateExamForm.invigilator = row.invigilator
  updateExamForm.courseId = row.courseId
}

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/exam/insertExam' , addExamForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('添加成功')
          closeAddDialog()
          reset()
        } else {
          ElMessage.error('添加失败')
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
      request.post('/exam/updateExam', updateExamForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('更新成功')
          closeUpdateDialog()
          reset()
        } else {
          ElMessage.error('更新失败')
          console.log(updateForm())
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

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}

const name = ref('')

const getList = () => {
  request.get("/exam/listExam?name="+encodeURIComponent(name.value)).then(res => {
    if(res.code === 200){
      userList.value = res.data
      console.log(res.data)
    } else {
      ElMessage.error(res.message)
    }
  })
}

const teacherList = ref([])
const getTeacherList = () =>{
  request.get("/teacher/listTeacherName").then(res => {
    if(res.code === 200){
      teacherList.value = res.data
      console.log(teacherList.value)
    } else {
      ElMessage.error("listTeacherError")
    }
  })
}

const courseList = ref([])
function getCourseList(){
  request.get("/course/listCourse").then(res =>{
    if(res.code === 200){
      courseList.value = res.data
      console.log(teacherList.value)
    } else {
      ElMessage.error("listCourseError")
    }
  })
}

const reset = () =>{
  name.value = ''
  getList()
  getTeacherList()
  getCourseList()
}

const removeExam = (id) =>{
  request.delete("/exam/deleteExamById/" + id).then(res => {
    if(res.code === 200){
      ElMessage.success("删除成功")
      reset()
    } else {
      ElMessage.error(res.message)
    }
  })
}

onMounted(() => {
  getList()
  getTeacherList()
  getCourseList()
})

function formatDate(row ,col){
  let data = row[col.property]
  if(data == null)
    return null
  let date_time = new Date(data);
  return date_time.toLocaleDateString()
}

function formatTime(row, col){
  let data = row[col.property]
  if(data == null)
    return null
  let date_time = new Date(data)
  return date_time.toLocaleString()
}
</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-input v-model="name" placeholder="请输入" class="input-with-select" style="height: 30px; width: 300px"></el-input>
        <el-button @click="getList" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
      </div>
      <div style="height: 15%; width: 100%"/>
      <div style="width: 100%; height: 50%; display: flex">

        <el-button @click="reset" type="danger" plain><el-icon style="margin-right: 3px"><Refresh /></el-icon>重置</el-button>
        <el-button type="success" plain @click="addExamDialog = true"><el-icon style="margin-right: 3px"><Plus /></el-icon>新增</el-button>
      </div>
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="userList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="name" label="考试名称" width="130" />
        <el-table-column prop="date" label="考试日期" width="130" :formatter="formatDate"/>
        <el-table-column prop="startTime" label="开始时间" width="180" :formatter="formatTime"/>
        <el-table-column prop="endTime" label="结束时间" width="180" :formatter="formatTime"/>
        <el-table-column prop="examPlace" label="考试地点" width="130" />
        <el-table-column prop="examDuration" label="考试时长(小时)" width="130" />
        <el-table-column prop="invigilator" label="监考老师" width="130" />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="openUpdateDialog(scope.row)"><el-icon style="margin-right: 5px"><Edit /></el-icon>修改</el-button>
            <el-popconfirm title="你确定要删除这个考试吗?" @confirm="removeExam(scope.row.id)" confirm-button-text="确定" cancel-button-text="取消">
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
      v-model="addExamDialog"
      title="新增考试信息"
      width="30%"
      :before-close="closeAddDialog"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="addExamForm" label-width="120px" :rules="rules" ref="ruleForm">
        <el-form-item label="考试名称" prop="name">
          <el-input v-model="addExamForm.name" />
        </el-form-item>
        <el-form-item label="考试日期" prop="date">
          <el-date-picker
              v-model="addExamForm.date"
              placeholder="请选择考试日期"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-time-picker
              v-model="addExamForm.startTime"
              start="08:00"
              step="00:15"
              end="20:45"
              placeholder="开始时间"
          />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-time-picker
              v-model="addExamForm.endTime"
              start="08:00"
              step="00:15"
              end="20:45"
              placeholder="结束时间"
          />
        </el-form-item>
        <el-form-item label="考试地点" prop="examPlace">
          <el-input v-model="addExamForm.examPlace" />
        </el-form-item>
        <el-form-item label="考试时长" prop="examDuration">
          <el-select v-model="addExamForm.examDuration" placeholder="请选择考试时长">
            <el-option label="1小时" value="1" />
            <el-option label="1.5小时" value="1.5" />
            <el-option label="2小时" value="2" />
            <el-option label="2.5小时" value="2.5" />
            <el-option label="3小时" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="监考老师" prop="invigilator">
          <el-select v-model="addExamForm.invigilator" class="m-2" placeholder="请选择监考老师">
            <el-option
                v-for="item in teacherList"
                :key="item.id"
                :label="item.username"
                :value="item.username"
            />
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
      v-model="updateExamDialog"
      title="更新考试信息"
      width="30%"
      :before-close="closeUpdateDialog"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="updateExamForm" label-width="120px" :rules="rules" ref="ruleForm">
        <el-form-item label="考试名称" prop="name">
          <el-input v-model="updateExamForm.name" />
        </el-form-item>
        <el-form-item label="考试日期" prop="date">
          <el-date-picker
              v-model="updateExamForm.date"
              placeholder="请选择考试日期"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-time-picker
              v-model="updateExamForm.startTime"
              start="08:00"
              step="00:15"
              end="20:45"
              placeholder="开始时间"
          />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-time-picker
              v-model="updateExamForm.endTime"
              start="08:00"
              step="00:15"
              end="20:45"
              placeholder="结束时间"
          />
        </el-form-item>
        <el-form-item label="考试地点" prop="examPlace">
          <el-input v-model="updateExamForm.examPlace" />
        </el-form-item>
        <el-form-item label="考试时长" prop="examDuration">
          <el-select v-model="updateExamForm.examDuration" placeholder="请选择考试时长">
            <el-option label="1小时" value="1" />
            <el-option label="1.5小时" value="1.5" />
            <el-option label="2小时" value="2" />
            <el-option label="2.5小时" value="2.5" />
            <el-option label="3小时" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="监考老师" prop="invigilator">
          <el-select v-model="updateExamForm.invigilator" class="m-2" placeholder="请选择监考老师">
            <el-option
                v-for="item in teacherList"
                :key="item.value"
                :label="item.username"
                :value="item.username"
            />
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