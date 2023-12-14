<script setup>

import {ArrowRight, Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import {useInfoStore} from "@/stores/pinna";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const leaveList = ref([])
const userInfo = useInfoStore()
const addLeaveDialog = ref(false)
let user = JSON.parse(localStorage.getItem("user"))
const ruleForm = ref()

const addLeaveForm = reactive({
  userId: user.id,
  leaveBegin: '',
  leaveEnd: '',
  leaveTime: '',
  leaveReason: '',
  leaveType: ''
})

function formatDate(row ,col){
  let data = row[col.property]
  if(data == null)
    return null
  let date_time = new Date(data);
  return date_time.toLocaleDateString()
}

function formatAccepted(row, col){
  let data = row[col.property]
  if(data === 1){
    return "通过"
  } else if(data === null){
    return "审核中"
  } else if(data === 0){
    return "未通过"
  }
}

function getLeaveList(){
  request.get("/leave/getLeaveListByUserId?userId="+user.id).then(res => {
    if(res.code === 200){
      leaveList.value = res.data
    } else {
      console.log(res.message)
    }
  })
}

const addLeave = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/leave/addLeave' , addLeaveForm).then(res =>{
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

const resetForm = (formEl) => {
  if (!formEl) return
  formEl.resetFields()
}

function closeAddDialog(){
  addLeaveDialog.value = false
}

function openAddDialog(){
  addLeaveDialog.value = true
}

function reset(){
  getLeaveList()
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
    <div class="header-wrapper">
      <div style="width: 100%; height: 20%; display: flex">
        <el-text style="font-size: 25px">请假申请</el-text>
      </div>
      <div style="height: 20%"/>
      <div style="width: 100%; height: 60%">
        <el-descriptions title="个人信息" border>
          <el-descriptions-item label="姓名">{{user.username}}</el-descriptions-item>
          <el-descriptions-item label="性别">{{user.gender}}</el-descriptions-item>
          <el-descriptions-item label="Net ID">{{user.netId}}</el-descriptions-item>
          <el-descriptions-item label="学号">{{user.code}}</el-descriptions-item>
          <el-descriptions-item label="学院">{{user.affiliated_school}}</el-descriptions-item>
          <el-descriptions-item label="住址">{{user.address}}</el-descriptions-item>
        </el-descriptions>
        <el-button type="primary" style="display: flex; margin-top: 10px" @click="openAddDialog">请假</el-button>
      </div>
    </div>
    <div style="height: 10%; display: flex; margin-top: 10px">
      <h4>请假记录</h4>
    </div>
    <el-scrollbar class="table-wrapper">
      <el-table :data="leaveList" stripe style="width: 100%; " border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="leaveBegin" label="开始日期" width="180" :formatter="formatDate"/>
        <el-table-column prop="leaveEnd" label="结束日期" width="180" :formatter="formatDate"/>
        <el-table-column prop="leaveTime" label="请假时长" width="100"/>
        <el-table-column prop="accepted" label="审批状态" :formatter="formatAccepted"/>
      </el-table>
    </el-scrollbar>
  </div>

  <el-dialog
      v-model="addLeaveDialog"
      title="请假申请"
      width="30%"
      :before-close="closeAddDialog"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="addLeaveForm" label-width="120px" ref="ruleForm">
        <el-form-item label="请假开始日期">
          <el-date-picker
              v-model="addLeaveForm.leaveBegin"
              type="date"
              placeholder="选择请假开始日期"
          />
        </el-form-item>
        <el-form-item label="请假结束日期">
          <el-date-picker
              v-model="addLeaveForm.leaveEnd"
              type="date"
              placeholder="选择请假结束日期"
          />
        </el-form-item>
        <el-form-item label="请假类型">
          <el-select v-model="addLeaveForm.leaveType" placeholder="请选择请假类型">
            <el-option label="事假" value="事假" />
            <el-option label="病假" value="病假" />
            <el-option label="丧假" value="丧假" />
          </el-select>
        </el-form-item>
        <el-form-item label="请假原因">
          <el-input
              v-model="addLeaveForm.leaveReason"
              :autosize="{ minRows: 2 }"
              type="textarea"
              placeholder="请输入请假原因"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="addLeave(ruleForm)">提交</el-button>
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