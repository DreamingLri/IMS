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

function withdrawCourse(row){
  let useCourse = {}
  useCourse.userId = userInfo.user.id
  useCourse.courseId = row.id

  request.post("/course/withdrawCourse", useCourse).then(res =>{
    if(res.code === 200){
      ElMessage.success("退课成功")
      getSelectedCourse()
      console.log(courseSelectedList)
    } else {
      console.log(res.message)
    }
  })
}

function selectCourse(row){
  let useCourse = {}
  useCourse.userId = userInfo.user.id
  useCourse.courseId = row.id

  request.post("/course/selectCourse", useCourse).then(res =>{
    if(res.code === 200){
      ElMessage.success("选课成功")
      getSelectedCourse()
      console.log(courseSelectedList)
    } else {
      console.log(res.message)
    }
  })
}

onMounted(()=> {
  getSelectedCourse()
})
</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-input v-model="name" placeholder="搜索课程" class="input-with-select" style="height: 30px; width: 300px"></el-input>
        <el-button @click="getSelectedCourse" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
        <el-button @click="reset" type="danger" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Refresh /></el-icon>重置</el-button>
      </div>
      <div style="height: 15%; width: 100%"/>
    </div>
    <div class="title">选课列表</div>
    <el-scrollbar class="table-wrapper1">
      <el-table :data="courseSelectedList" stripe style="width: 100%" border
                :default-sort="{ prop: 'selected', order: 'descending' }"
      >
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
            <el-button v-if="scope.row.selected === true" type="danger" plain @click="withdrawCourse(scope.row)"><el-icon style="margin-right: 5px"><CircleClose /></el-icon>退课</el-button>
            <el-button v-if="scope.row.selected === false" type="primary" plain @click="selectCourse(scope.row)"><el-icon style="margin-right: 5px"><CircleCheck /></el-icon>选课</el-button>
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
  height: 85%;
  width: 100%;
}
.title{
  text-align: left;
  margin-bottom: 10px;
  font-size: large;
}
</style>