<script setup>
import {ArrowRight, More} from "@element-plus/icons-vue";
import {onMounted, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const evaluationList = ref([])
let user = JSON.parse(localStorage.getItem("user"))
const evaluationDialog = ref(false)
const row = ref()

function getEvaluationList(){
  request.get("/score/getTeacherEvaluationByUserId?userId="+user.id).then(res=>{
    if(res.code === 200){
      evaluationList.value = res.data
    } else {
      ElMessage.error("获取评教出错")
      console.log(res.message)
    }
  })
}

function openEvaluationDialog(Row){
  evaluationDialog.value = true;
  row.value = Row
}

function closeEvaluationDialog(){
  evaluationDialog.value = false
}

const debounce = (fn, delay) => {
  let timer = null;

  return function () {
    let context = this;

    let args = arguments;

    clearTimeout(timer);

    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  };
};

const _ResizeObserver = window.ResizeObserver;

window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 16);
    super(callback);
  }
};

onMounted(()=>{
  getEvaluationList()
})
</script>

<template>
  <div class="main-wrapper">
    <div style="height: 5%">
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/teacher/dashboard' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>我的评教</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="header-wrapper">
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="evaluationList" stripe style="width: 100%" border>
        <el-table-column prop="course.id" label="ID" width="50" />
        <el-table-column prop="course.name" label="课程名称" width="180" />
        <el-table-column prop="course.place" label="上课地点" width="130" />
        <el-table-column prop="course.openedBy" label="开课学院" width="130" />
        <el-table-column prop="course.teacher" label="开课老师" width="130" />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="openEvaluationDialog(scope.row)"><el-icon style="margin-right: 5px"><More /></el-icon>详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-scrollbar>
  </div>

  <el-dialog
      v-model="evaluationDialog"
      title="评教详情"
      width="30%"
      :before-close="closeEvaluationDialog"
      style="border-radius: 8px; width: 50%"
  >
    <div style="margin-right: 20%">
      <el-scrollbar>
        <el-table :data="row.course.evaluationList" style="width: 100%">
          <el-table-column prop="id" label="ID" width="180" />
          <el-table-column prop="evaluationScore" label="评教分数" width="180" />
          <el-table-column prop="evaluationSuggestion" label="评教建议" />
        </el-table>
      </el-scrollbar>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeEvaluationDialog">关闭</el-button>
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
  height: 5%;
}
.table-wrapper{
  height: 85%;
  width: 100%;
}
</style>