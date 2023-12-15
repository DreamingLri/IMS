<script setup>

import {Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {useInfoStore} from "@/stores/pinna";


const course = ref('')
const courseList = ref([])
const courseId = ref()
const scoreDialog = ref(false)
const ruleForm = ref()
const userId = ref()
const name = ref('')
const studentId = ref()
const studentList = ref([])

const userInfo = useInfoStore()

const addScoreForm = reactive({
  id: '',
  courseId: courseId,
  userId: userInfo.user.id,
  evaluationScore: '',
  evaluationSuggestion: ''
})

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/score/addEvaluation' , addScoreForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('添加成功')
          scoreDialogClose()
          getCourseList()
        } else {
          ElMessage.error('添加失败')
          console.log(res.message)
        }
      })
    } else {
      ElMessage.error('请检查填入的信息是否正确')
      console.log('error submit!', fields)
    }
  })
}

function getCourseList(){
  request.get('/score/listTeacherWithScoreByUserId?userId='+studentId.value).then(res => {
    if(res.code === 200){
      courseList.value = res.data
    } else {
      console.log(res.message)
    }
  })
}
onMounted(() =>{
  getStudentList()
})

function openScoreDialog(row){
  scoreDialog.value = true
  courseId.value = row.id
  addScoreForm.evaluationScore = row.evaluationScore
}

function closeScoreDialog(){
  scoreDialog.value = false;
}

function scoreDialogClose(){
  scoreDialog.value = false
}

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}

const rules = reactive({
  suggestion: [
    { max: 200, message: '评教建议最长为200词'}
  ]
})

function getStudentList(){
  request.get("/student/listStudent").then(res=>{
    if(res.code === 200){
      studentList.value = res.data
    } else {
      ElMessage.error("get student list error")
      console.log(res.message)
    }
  })
}

</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <div style="width: 100%; height: 40%; display: flex">
          <el-select v-model="studentId" class="m-2" placeholder="选择学生" style="height: 30px; width: 300px">
            <el-option v-for="item in studentList" :key="item.id" :label="item.username" :value="item.id"/>
          </el-select>
          <el-button @click="getCourseList()" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
        </div>
      </div>
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="courseList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="teacherName" label="教师名称" width="130" />
        <el-table-column prop="courseName" label="课程名称" width="130" />
        <el-table-column prop="evaluationScore" label="评教分" width="130" />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="openScoreDialog(scope.row)"><el-icon style="margin-right: 5px"><Edit /></el-icon>评教</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-scrollbar>
  </div>

  <el-dialog
      v-model="scoreDialog"
      title="评分"
      width="30%"
      :before-close="scoreDialogClose"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="addScoreForm" label-width="120px" ref="ruleForm" :rules="rules">
        <el-form-item label="评教分" prop="name">
          <el-input v-model="addScoreForm.evaluationScore" />
        </el-form-item>
        <el-form-item label="评教建议" prop="suggestion">
          <el-input
              v-model="addScoreForm.evaluationSuggestion"
              :autosize="{ minRows: 2 }"
              type="textarea"
              placeholder="请输入评教建议"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleForm)">提交评分</el-button>
          <el-button @click="resetForm(ruleForm)">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeScoreDialog">取消</el-button>
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