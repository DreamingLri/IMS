<script setup>

import {CircleCheck, CircleClose, Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {computed, onMounted, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const examList = ref([])
const studentList = ref([])
const selectedStudent = ref([])
// const selectedStudentFunction = computed({
//   get(){
//     return getSelectStudentList()
//   },
//   set(){
//     addStudent()
//   }
// })
function getExamList(){
  request.get("/exam/listExam").then(res =>{
    if(res.code === 200){
      examList.value = res.data
    } else {
      ElMessage.error("listExamError")
    }
  })
}

function getSelectStudentList(row){
  request.get("/exam/listStudentByExamId?examId=" + row.id).then(res => {
    if(res === 200){
      selectedStudent.value = res.data
    } else {
      ElMessage.error("selectStudentError")
    }
  })
  return selectedStudent;
}

function getStudentList(){
  request.get("student/listStudent?name=" + '').then(res =>{
    if(res.code === 200){
      studentList.value = res.data
    } else {
      ElMessage.error("listStudentError")
    }
  })
}

onMounted(()=>{
  getExamList()
  getStudentList()
})

function reset(){
  getExamList()
  getStudentList()
  name.value = ''
}

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

function addStudent(row){
  let result = {}
  result.id = row.id
  result.student = selectedStudent.value.toString()
  request.post('/exam/addStudent', result).then(res=>{
    if(res.code === 200){
      console.log(result)
    } else {
      console.log(result)
    }
  })
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
          <el-table-column label="报考学生" width="400">
            <template v-slot="scope">
              <el-select
                  v-model="selectedStudent"
                  multiple
                  placeholder="Select"
                  style="width: 100%"
              >
                <el-option
                    v-for="item in studentList"
                    :key="item.id"
                    :label="item.username"
                    :value="item.id"
                />
              </el-select>
            </template>

          </el-table-column>
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button type="primary" plain @click="addStudent(scope.row)"><el-icon style="margin-right: 5px"><CircleClose /></el-icon>添加学生</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </div>
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