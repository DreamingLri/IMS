<script setup>

import {ArrowRight, Check, Close, Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import {useInfoStore} from "@/stores/pinna";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const leaveList = ref([])
let user = JSON.parse(localStorage.getItem("user"))
const ruleForm = ref()


function formatDate(row ,col){
  let data = row[col.property]
  if(data == null)
    return null
  let date_time = new Date(data);
  return date_time.toLocaleDateString()
}

function formatAccepted(row, col){
  let data = row[col.property]
  if(data == 1){
    return "通过"
  } else if(data == null){
    return "审核中"
  } else if(data == 0){
    return "未通过"
  }
}

function getLeaveList(){
  request.get("/leave/getAllListByAdminId?userId="+user.id).then(res => {
    if(res.code === 200){
      leaveList.value = res.data
    } else {
      console.log(res.message)
    }
  })
}

function acceptLeave(row){
  let leave = {
    id: row.id,
    accepted: 1
  }
  request.post("/leave/updateLeave", leave).then(res=>{
    if(res.code === 200){
      ElMessage.success("准假成功")
      getLeaveList()
    } else {
      ElMessage.error("准假失败")
    }
  })
}
function rejectLeave(row){
  let leave = {
    id: row.id,
    accepted: 0
  }
  request.post("/leave/updateLeave", leave).then(res=>{
    if(res.code === 200){
      ElMessage.success("驳回成功")
      getLeaveList()
    } else {
      ElMessage.error("失败失败")
    }
  })
}

onMounted(()=>{
  getLeaveList()
})
</script>

<template>
  <div class="main-wrapper">
    <div style="height: 5%">
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/student/dashboard' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>请假申请</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="height: 10%; display: flex; margin-top: 10px">
      <h4>请假记录</h4>
    </div>
    <el-scrollbar class="table-wrapper">
      <el-table :data="leaveList" stripe style="width: 100%; " border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="leaveBegin" label="开始日期" width="180" :formatter="formatDate"/>
        <el-table-column prop="leaveEnd" label="结束日期" width="180" :formatter="formatDate"/>
        <el-table-column prop="leaveTime" label="请假时长" width="100"/>
        <el-table-column prop="leaveType" label="请假类型" width="100"/>
        <el-table-column prop="leaveReason" label="请假理由" width="250"/>
        <el-table-column prop="accepted" label="审批状态" width="80" :formatter="formatAccepted"/>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="acceptLeave(scope.row)"><el-icon style="margin-right: 5px"><Check /></el-icon>准假</el-button>
            <el-button type="danger" plain @click="rejectLeave(scope.row)"><el-icon style="margin-right: 5px"><Close /></el-icon>驳回</el-button>
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
  height: 30%;
}
.table-wrapper{
  height: 68%;
  width: 100%;
}
</style>