<script setup>
import {CircleCheck, CircleClose, Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {useInfoStore} from "@/stores/pinna";

const courseList = ref([])
const courseSelectedList = ref([])
const userInfo = useInfoStore()

const name = ref('')
function getCourseList(){
  request.get("/course/listCourse"+name.value).then(res => {
    if(res.code === 200){
      courseList.value = res.data
    } else {
      ElMessage.error(res.message)
    }
  })
}

function getSelectedCourse(){
  request.get("/course/listCourseById?id="+userInfo.user.id).then(res =>{
    if(res.code === 200){
      courseSelectedList.value = res.data
    } else {
      ElMessage.error(res.message)
    }
  })
}

function reset(){
  getCourseList()
  getSelectedCourse()
  name.value = ''
}

function formatDate(row ,col){
  let data = row[col.property]
  if(data == null)
    return null
  let date_time = new Date(data);
  return date_time.toLocaleDateString()
}

onMounted(()=> {
  getCourseList()
  getSelectedCourse()
})
</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-input v-model="name" placeholder="请输入" class="input-with-select" style="height: 30px; width: 300px"></el-input>
        <el-button @click="getCourseList" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
      </div>
      <div style="height: 15%; width: 100%"/>
      <div style="width: 100%; height: 50%; display: flex">
        <el-button @click="reset" type="danger" plain><el-icon style="margin-right: 3px"><Refresh /></el-icon>重置</el-button>
      </div>
    </div>

    <div style="height: 5%; width: 100%"/>
    <h2>已选课程</h2>
    <el-scrollbar class="table-wrapper1">
      <el-table :data="courseSelectedList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50"/>
        <el-table-column prop="name" label="课程名称" width="130" />
        <el-table-column prop="openedBy" label="开课学院" width="130" />
        <el-table-column prop="teacher" label="开课老师" width="130" />
        <el-table-column prop="credit" label="学分" width="70" />
        <el-table-column prop="studentNumber" label="选课人数" width="100" />
        <el-table-column prop="period" label="学时" width="100" />
        <el-table-column prop="startTime" label="开始日期" width="100" :formatter="formatDate"/>
        <el-table-column prop="endTime" label="结束日期" width="100" :formatter="formatDate"/>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="danger" plain @click="withdrawCourse(scope.row)"><el-icon style="margin-right: 5px"><CircleClose /></el-icon>退课</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-scrollbar>

    <el-divider/>
    <h2>可选课程</h2>
    <el-scrollbar class="table-wrapper2">
      <el-table :data="courseList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50"/>
        <el-table-column prop="name" label="课程名称" width="130" />
        <el-table-column prop="openedBy" label="开课学院" width="130" />
        <el-table-column prop="teacher" label="开课老师" width="130" />
        <el-table-column prop="credit" label="学分" width="70" />
        <el-table-column prop="studentNumber" label="选课人数" width="100" />
        <el-table-column prop="period" label="学时" width="100" />
        <el-table-column prop="startTime" label="开始日期" width="100" :formatter="formatDate"/>
        <el-table-column prop="endTime" label="结束日期" width="100" :formatter="formatDate"/>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="selectCourse(scope.row)"><el-icon style="margin-right: 5px"><CircleCheck /></el-icon>选课</el-button>
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

.table-wrapper1{
  height: 25%;
  width: 100%;
}
.table-wrapper2{
  height: 40%;
  width: 100%;
}
</style>