<script setup>

import {ArrowRight, Edit, Refresh} from "@element-plus/icons-vue";
import {onMounted, ref} from "vue";
import request from "@/utils/request";
import {useInfoStore} from "@/stores/pinna";
import {ElMessage} from "element-plus";

const scoreList = ref([])
const userInfo = useInfoStore()
let user = JSON.parse(localStorage.getItem("user"))

function getScoreList(){
  request.get('score/listScoreByUserId?userId='+user.id).then(res=>{
    if(res.code === 200){
      scoreList.value = res.data
    } else {
      ElMessage.error("更新成绩失败")
    }
  })
}
onMounted(()=>{
  getScoreList()
})
</script>

<template>
  <div class="main-wrapper">
    <div style="height: 5%">
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/student/dashboard' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>我的成绩</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="header-wrapper">
      <div style="width: 100%; height: 20%; display: flex">
        <el-text style="font-size: 25px">我的成绩</el-text>
      </div>
    </div>
    <el-scrollbar class="table-wrapper">
      <el-table :data="scoreList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="name" label="课程名" width="130" />
        <el-table-column prop="studyScore" label="平时分" width="130" />
        <el-table-column prop="examScore" label="考试分" width="130" />
        <el-table-column prop="totalScore" label="总分" width="130"/>
        <el-table-column prop="gradePoint" label="绩点"/>
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