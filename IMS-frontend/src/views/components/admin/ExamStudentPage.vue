<script setup>

import {CircleCheck, CircleClose, Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {computed, onMounted, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const examList = ref([])
const selectedStudent = ref([])
const dialogVisible = ref(false)
const examId = ref()

function getExamList(){
  request.get("/exam/listExam").then(res =>{
    if(res.code === 200){
      examList.value = res.data
    } else {
      ElMessage.error("listExamError")
    }
  })
}

function getSelectStudentList(id){
  request.get("/user-exam/listUserSelectedExam?examId=" + id).then(res => {
    if(res.code === 200){
      selectedStudent.value = res.data
    } else {
      ElMessage.error("selectStudentError")
    }
  })
  return selectedStudent;
}


onMounted(()=>{
  getExamList()
})

function reset(){
  getExamList()
  name.value = ''
}

function formatDate(row ,col){
  let data = row[col.property]
  if(data == null)
    return null
  let date_time = new Date(data);
  return date_time.toLocaleDateString()
}


function addStudent(id){
  dialogVisible.value = true
  getSelectStudentList(id)
  examId.value = id;
}

function selectExam(row){
  let returnFrom = {}
  returnFrom.userId = row.id
  returnFrom.examId = examId.value

  request.post('/exam/selectExam' , returnFrom).then(res =>{
    if(res.code === 200){
      ElMessage.success("选考成功")
      getSelectStudentList(examId.value)
    } else {
      ElMessage.error("选考失败")
    }
  })
}

function withdrawExam(row){
  let returnFrom = {}
  returnFrom.userId = row.id
  returnFrom.examId = examId.value

  request.post("/exam/withdrawExam" , returnFrom).then(res =>{
    if(res.code === 200){
      ElMessage.warning("退考成功")
      getSelectStudentList(examId.value)
    } else {
      ElMessage.error("退考失败")
    }
  })
}

function handleClose(){
  dialogVisible.value = false
}

function formatSelected(row, col){
  let data = row[col.property]
  if(data === true){
    return "是"
  } else {
    return "否"
  }
}
</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-input v-model="name" placeholder="请输入" class="input-with-select" style="height: 30px; width: 300px"></el-input>
        <el-button @click="getExamList" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
        <el-button @click="reset" type="danger" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Refresh /></el-icon>重置</el-button>
      </div>
      <div style="height: 5%; width: 100%"/>
    </div>
    <div style="height: 5%; width: 100%"/>
      <el-scrollbar class="table-wrapper">
        <el-table :data="examList" stripe style="width: 100%" border>
          <el-table-column prop="id" label="ID" width="50" />
          <el-table-column prop="name" label="考试名称" width="130" />
          <el-table-column prop="date" label="考试日期" width="130" :formatter="formatDate"/>
          <el-table-column prop="invigilator" label="监考老师" width="130"/>
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button type="primary" plain @click="addStudent(scope.row.id)"><el-icon style="margin-right: 5px"><CircleClose /></el-icon>添加学生</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
  <el-dialog
      v-model="dialogVisible"
      title="添加选考学生"
      width="25%"
      height="60%"
      :before-close="handleClose"
  >
    <el-table :data="selectedStudent" stripe style="width: 100%" border
              :default-sort="{ prop: 'selected', order: 'descending' }"
    >
      <el-table-column prop="id" label="ID" width="50"/>
      <el-table-column prop="username" label="姓名" width="100"/>
      <el-table-column prop="selected" label="是否选考" width="100" :formatter="formatSelected"/>
      <el-table-column label="操作">
        <template v-slot="scope1">
          <el-button v-if="scope1.row.selected === false" type="primary" plain @click="selectExam(scope1.row)"><el-icon style="margin-right: 5px"><CircleClose /></el-icon>选考</el-button>
          <el-button v-if="scope1.row.selected === true" type="danger" plain @click="withdrawExam(scope1.row)"><el-icon style="margin-right: 5px"><CircleClose /></el-icon>退考</el-button>
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确认</el-button>
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