<script setup>

import {Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";


const course = ref('')
const courseList = ref([])
const userList = ref([])
const courseId = ref()
const scoreDialog = ref(false)
const ruleForm = ref()
const userId = ref()

const addScoreForm = reactive({
  id: '',
  courseId: courseId,
  userId: userId,
  studyScore: '',
  examScore: '',
  scoreFunction: '1',
  totalScore: '',
  evaluationScore: '',
})

function getCourseList(){
  request.get("/course/listCourse").then(res =>{
    if(res.code === 200){
      courseList.value = res.data;
    } else {
      console.log(res.message)
    }
  })
}

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/score/addScore' , addScoreForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('添加成功')
          scoreDialogClose()
          getUserList(addScoreForm.courseId)
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

function getUserList(courseId){
  request.get('/score/listStudentWithScoreByCourseId?courseId='+courseId).then(res => {
    if(res.code === 200){
      userList.value = res.data
    } else {
      console.log(res.message)
    }
  })
}
onMounted(() =>{
  getCourseList()
})

function openScoreDialog(row){
  scoreDialog.value = true
  userId.value = row.id

  addScoreForm.examScore = row.examScore
  addScoreForm.studyScore = row.studyScore
  addScoreForm.totalScore = row.totalScore
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

function formatScore(row, col){
  let data = row[col.property]
  if(data == null)
    return "暂未评分"
  if(data === 1){
    return "平时分60%, 考试分40%"
  } else if(data === 2){
    return "平时分50%, 考试分50%"
  } else {
    return "平时分100%"
  }
}

</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-select v-model="courseId" class="m-2" placeholder="请选择课程" style="height: 30px; width: 300px">
          <el-option
              v-for="item in courseList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
        <el-button @click="getUserList(courseId)" type="primary" plain style="margin-left: 10px; height: 31px"><el-icon style="margin-right: 3px"><Refresh /></el-icon>选择</el-button>
      </div>
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="userList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="netId" label="Net ID" width="130" />
        <el-table-column prop="name" label="姓名" width="130" />
        <el-table-column prop="code" label="学号" width="130" />
        <el-table-column prop="studyScore" label="平时分" width="130" />
        <el-table-column prop="examScore" label="考试分" width="130" />
        <el-table-column prop="totalScore" label="总分" width="130" />
        <el-table-column prop="scoreFunction" label="评分规则" width="180" :formatter="formatScore"/>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="openScoreDialog(scope.row)"><el-icon style="margin-right: 5px"><Edit /></el-icon>评分</el-button>
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
      <el-form :model="addScoreForm" label-width="120px" ref="ruleForm">
        <el-form-item label="平时分" prop="name">
          <el-input v-model="addScoreForm.studyScore" />
        </el-form-item>
        <el-form-item label="考试分" prop="name">
          <el-input v-model="addScoreForm.examScore" />
        </el-form-item>
        <el-form-item label="评分模式">
          <el-select v-model="addScoreForm.scoreFunction" class="m-2" placeholder="Select">
            <el-option label="平时分60%, 考试分40%" value="1"/>
            <el-option label="平时分50%, 考试分50%" value="2"/>
            <el-option label="平时分100%" value="3"/>
          </el-select>
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