<script setup>
import {onMounted, reactive, ref} from "vue";
import {Location, Search, User} from "@element-plus/icons-vue";
import request from "@/utils/request";
import {useInfoStore} from "@/stores/pinna";
import {ElMessage} from "element-plus";

const tableData = reactive([])
const studentList = ref([])
const studentId = ref()
let user = JSON.parse(localStorage.getItem("user"))

function getTableData(){
  request.get("/course/getCourseTable/" + studentId.value).then(res => {
    if(res.code === 200){
      tableData.value = res.data;
    } else {
      console.log(res.message)
    }
  })
}

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

onMounted(()=>{
  getStudentList()
})
</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-select v-model="studentId" class="m-2" placeholder="请选择学生" style="height: 30px; width: 300px">
          <el-option v-for="item in studentList" :key="item.id" :label="item.username" :value="item.id"/>
        </el-select>
        <el-button @click="getTableData" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>选择</el-button>
      </div>
      <h3 style="display: flex">课程表</h3>
    </div>
      <el-table border stripe :data="tableData.value" :header-cell-style="{background:'#eee',color:'#606266'}">
        <el-table-column label="节次">
          <template v-slot="scope">
            <div v-if="scope.row.session">
              <div style="margin: 10px 0; text-align: center;">{{scope.row.session.num}}</div>
              <div style="margin: 10px 0; text-align: center; color: rgb(145,208,164)">{{scope.row.session.time}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="星期日">
          <template v-slot="scope">
            <div v-if="scope.row.sun">
              <div style="margin: 10px 0">{{scope.row.sun.name}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.sun.teacher}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.sun.place}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="星期一">
          <template v-slot="scope">
            <div v-if="scope.row.mon">
              <div style="margin: 10px 0">{{scope.row.mon.name}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.mon.teacher}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.mon.place}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="星期二">
          <template v-slot="scope">
            <div v-if="scope.row.tue">
              <div style="margin: 10px 0">{{scope.row.tue.name}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.tue.teacher}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.tue.place}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="星期三">
          <template v-slot="scope">
            <div v-if="scope.row.wed">
              <div style="margin: 10px 0">{{scope.row.wed.name}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.wed.teacher}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.wed.place}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="星期四">
          <template v-slot="scope">
            <div v-if="scope.row.thu">
              <div style="margin: 10px 0">{{scope.row.thu.name}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.thu.teacher}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.thu.place}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="星期五">
          <template v-slot="scope">
            <div v-if="scope.row.fri">
              <div style="margin: 10px 0">{{scope.row.fri.name}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.fri.teacher}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.fri.place}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="星期六">
          <template v-slot="scope">
            <div v-if="scope.row.sat">
              <div style="margin: 10px 0">{{scope.row.sat.name}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><User /></el-icon>{{scope.row.sat.teacher}}</div>
              <div style="margin: 10px 0"><el-icon style="margin-right: 10px"><Location /></el-icon>{{scope.row.sat.place}}</div>
            </div>
          </template>
        </el-table-column>
      </el-table>
  </div>
</template>

<style scoped>
.headerBg{
  background: #eee!important;
}
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
</style>