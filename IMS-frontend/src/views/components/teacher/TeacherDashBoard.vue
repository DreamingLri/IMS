<script setup>
import {onMounted, reactive, ref} from "vue";
import { useInfoStore } from "@/stores/pinna";
import {ElMessage} from "element-plus";
import {
  Bell,
  Calendar, ChatDotRound, ChatLineRound,
  ChatLineSquare, CoffeeCup,
  Collection,
  DataAnalysis, DataBoard, EditPen,
  MapLocation,
  PieChart,
  School, Tickets, Timer
} from "@element-plus/icons-vue";
import ClassTable from "@/views/components/ClassTable.vue";
import router from "@/router"
import request from "@/utils/request";

const userInfo = useInfoStore()
console.log(localStorage.getItem("user"))
let user = JSON.parse(localStorage.getItem("user"))

onMounted(()=>{
  ElMessage.success(user.username + "，欢迎回来！")
  getUserList()
  getMessage()
})

//日历
const calendar = ref()
const selectDate = (val) => {
  if (!calendar.value) return
  calendar.value.selectDate(val)
}

//私信
const messageDialog = ref(false)
const direction = ref('rtl')
const messageList = ref([])
function cancelClick(){
  messageDialog.value = false
}
function getMessage(){
  request.get("/message/getMessageByUserId?userId="+user.id).then(res=>{
    if(res.code === 200){
      messageList.value = res.data
    } else {
      ElMessage.error("Get Message Error")
      console.log(res.message)
    }
  })
}
function openMessage(){
  messageDialog.value = true
  getMessage()
}
function reply(index, row){
  //交换收发人
  replyFrom.fromUserId = row.toUserId
  replyFrom.fromUserName = row.toUserName
  replyFrom.toUserId = row.fromUserId
  replyFrom.toUserName = row.fromUserName
  replyDialog.value = true
}
function addReply(){
  replyDialog.value = true
}
function sendMessage(){
  request.post("/message/sendMessage",replyFrom).then(res=>{
    if(res.code === 200){
      ElMessage.success("发送成功")
      replyDialog.value = false
    } else {
      ElMessage.error("发送失败")
    }
  })
}
const replyFrom = reactive({
  fromUserId: user.id,
  fromUserName: user.username,
  toUserId: '',
  toUserName: '',
  message: ''
})
const replyDialog = ref(false)
function openReply(){
  replyDialog.value = true
}
function closeReply(){
  replyDialog.value = false
}

const userList = ref([])
function getUserList(){
  request.get("/user/listUser").then(res=>{
    if(res.code === 200){
      userList.value = res.data
    } else {
      console.log(res.message)
    }
  })
}
function formatDate(data){
  if(data == null)
    return null
  let date_time = new Date(data);
  return date_time.toLocaleDateString()
}
</script>

<template>
  <div class="main-wrapper">
    <!--第一行-->
    <el-row :gutter="20">
      <!--系统收藏夹-->
      <el-col :span="24">
        <el-card class="system-entrance" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><MapLocation /></el-icon>系统收藏夹</el-text></span>
            </div>
          </template>
          <div>
            <el-row :gutter="12">
              <el-col :span="2" >
                <el-card shadow="hover" style="height: 80%" @click.native="router.push('/teacher/course')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30"><Collection /></el-icon>
                    <p style="font-size: 15px">我的课程</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2" >
                <el-card shadow="hover" style="height: 80%" @click.native="router.push('/teacher/teacher-score')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30"><ChatLineSquare /></el-icon>
                    <p style="font-size: 15px">我的评教</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%" @click.native="router.push('/teacher/course-status')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30" ><DataAnalysis /></el-icon>
                    <p style="font-size: 15px">选课情况</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%" @click.native="router.push('/teacher/course-time')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30"><Tickets /></el-icon>
                    <p style="font-size: 15px">我的排课</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%" @click.native="router.push('/teacher/leave')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30" ><CoffeeCup /></el-icon>
                    <p style="font-size: 15px">请假申请</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%" @click.native="router.push('/teacher/student-score')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30" ><EditPen /></el-icon>
                    <p style="font-size: 15px">评分系统</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%" @click.native="router.push('/teacher/class-table')">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30" ><DataBoard /></el-icon>
                    <p style="font-size: 15px">我的课表</p>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="2">
                <el-card shadow="hover" style="height: 80%" @click.native="openMessage">
                  <div>
                    <el-icon color="rgb(90,156,248)" size="30" ><ChatLineRound /></el-icon>
                    <p style="font-size: 15px">我的私信</p>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!--第二行-->
    <el-row :gutter="20">

      <el-col :span="8">
        <el-card class="panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><User /></el-icon>基本信息</el-text></span>
            </div>
          </template>
          <div>
            <el-descriptions
                :column="2"
                :style="blockMargin"
            >
              <el-descriptions-item label="姓名:">{{ user.username }}</el-descriptions-item>
              <el-descriptions-item label="Net ID:">{{ user.netId }}</el-descriptions-item>

              <el-descriptions-item label="性别:">{{ user.gender }}</el-descriptions-item>
              <el-descriptions-item label="住址:">{{ user.address }}</el-descriptions-item>

              <el-descriptions-item label="生日:">{{ formatDate(user.birthday) }}</el-descriptions-item>
              <el-descriptions-item label="身份证:">{{ user.identificationCode }}</el-descriptions-item>

              <el-descriptions-item><el-divider/></el-descriptions-item><el-descriptions-item><el-divider/></el-descriptions-item>

              <el-descriptions-item label="所属院系:">{{ user.affiliated_school }}</el-descriptions-item>
              <el-descriptions-item label="研究方向:">{{ user.researchDirection }}</el-descriptions-item>
              <el-descriptions-item label="职称:">{{ user.qualification }}</el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><ChatLineRound /></el-icon>私信</el-text></span>
            </div>
          </template>
          <div>
            <el-table :data="messageList" style="width: 100%">
              <el-table-column label="ID" width="50">
                <template #default="scope">
                  <el-popover effect="light" trigger="hover" placement="top" width="auto">
                    <template #default>
                      <div>From: {{ scope.row.fromUserName }}</div>
                      <div>To: {{ scope.row.toUserName }}</div>
                    </template>
                    <template #reference>
                      <el-tag>{{ scope.row.id }}</el-tag>
                    </template>
                  </el-popover>
                </template>
              </el-table-column>
              <el-table-column label="消息">
                <template #default="scope">
                  <el-popover effect="light" trigger="hover" placement="top" width="auto">
                    <template #reference>
                      <div style="display: flex; align-items: center">
                        <el-icon><ChatDotRound /></el-icon>
                        <span style="margin-left: 10px">{{ scope.row.message }}</span>
                      </div>
                    </template>
                    <template #default>
                      <div>{{scope.row.message}}</div>
                    </template>
                  </el-popover>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>

      <!--学院公告-->
      <el-col :span="10">
        <el-card class="panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><Bell /></el-icon>学校公告</el-text></span>
            </div>
          </template>
          <div style="height: 240px">
            <el-carousel :interval="5000" arrow="always" height="auto" autoplay>
              <el-carousel-item style="height: 240px">
                <img src="../../../../public/14th.jpg" alt="14th" width="600">
              </el-carousel-item>
              <el-carousel-item style="height: 240px">
                <img src="../../../../public/2023cun-sysu.jpg" alt="2023cun-sysu" width="600">
              </el-carousel-item>
              <el-carousel-item style="height: 240px">
                <img src="../../../../public/shouyezhuantitu.jpg" alt="shouyezhuantitu" width="600">
              </el-carousel-item>
              <el-carousel-item style="height: 240px">
                <img src="../../../../public/zt2023xjp-sysu.jpg" alt="zt2023xjp-sysu" width="600">
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!--第二行-->
    <el-row :gutter="20">
      <!--日历-->
      <el-col :span="10">
        <el-card class="second_panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><Calendar /></el-icon>日历</el-text></span>
            </div>
          </template>
          <div style="transform: scale(0.8); height: 0; width: 100%">
            <el-calendar ref="calendar">
              <template #header="{ date }">
                <span>{{ date }}</span>
                <el-button-group>
                  <el-button size="small" @click="selectDate('prev-month')">上一月</el-button>
                  <el-button size="small" @click="selectDate('today')">今天</el-button>
                  <el-button size="small" @click="selectDate('next-month')">下一月</el-button>
                </el-button-group>
              </template>
            </el-calendar>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!--第三行-->
    <el-row :gutter="20">
      <!--课程表-->
      <el-col :span="24">
        <el-card class="class_panel" shadow="hover">
          <template #header>
            <div class="card-header">
              <span><el-text style="font-size: 20px" size="large" type="primary"><el-icon style="margin-right: 5px"><School /></el-icon>课程表</el-text></span>
            </div>
          </template>
          <div>
            <class-table/>
          </div>
        </el-card>
      </el-col>

    </el-row>
  </div>

  <el-drawer v-model="messageDialog" :direction="direction">
    <template #header>
      <h4>我的私信</h4>
    </template>
    <template #default>
      <div>
        <el-table :data="messageList" style="width: 100%">
          <el-table-column label="ID" width="50">
            <template #default="scope">
              <el-popover effect="light" trigger="hover" placement="top" width="auto">
                <template #default>
                  <div>From: {{ scope.row.fromUserName }}</div>
                  <div>To: {{ scope.row.toUserName }}</div>
                </template>
                <template #reference>
                  <el-tag>{{ scope.row.id }}</el-tag>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="消息" width="300">
          <template #default="scope">
            <el-popover effect="light" trigger="hover" placement="top" width="auto">
              <template #reference>
                <div style="display: flex; align-items: center">
                  <el-icon><ChatDotRound /></el-icon>
                  <span style="margin-left: 10px">{{ scope.row.message }}</span>
                </div>
              </template>
              <template #default>
                <div>{{scope.row.message}}</div>
              </template>
            </el-popover>
          </template>
        </el-table-column>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button
                  size="small"
                  type="primary"
                  @click="reply(scope.$index, scope.row)"
              >回复</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>



    </template>
    <template #footer>
      <div class="demo-drawer__footer">
        <el-button type="primary" @click="addReply">发消息</el-button>
        <el-button @click="closeReply">取消</el-button>
      </div>
    </template>
  </el-drawer>

  <el-dialog
      v-model="replyDialog"
      title="新建信息"
      width="30%"
      :before-close="closeReply"
  >
    <el-form :model="replyFrom" label-width="120px" :rules="rules" ref="ruleForm">
      <el-form-item label="发信人">
        <el-input v-model="replyFrom.fromUserName" placeholder="Please input" />
      </el-form-item>
      <el-form-item label="收信人">
        <el-select v-model="replyFrom.toUserId" class="m-2" placeholder="收信人">
        <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.username"
            :value="item.id"
        />
        </el-select>
      </el-form-item>
      <el-form-item label="信息">
        <el-input
            v-model="replyFrom.message"
            :autosize="{ minRows: 2 }"
            type="textarea"
            placeholder="请输入信息"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="replyDialog = false">取消</el-button>
        <el-button type="primary" @click="sendMessage">发送</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.main-wrapper{
  width: 100%;
  height: 100%;
  border-radius: 8px;
  box-sizing: border-box;
  padding: 20px;
}

.system-entrance{
  height: 230px;
  width: 100%;
  background-color: white;
  border-radius: 4px;
  box-sizing: border-box;
  padding: 5px;
}

.panel{
  height: 360px;
  width: 100%;
  background-color: white;
  border-radius: 4px;
  box-sizing: border-box;
  padding: 5px;
}

.class_panel{
  height: 1400px;
  width: 100%;
  background-color: white;
  border-radius: 4px;
  box-sizing: border-box;
  padding: 5px;
}
.second_panel{
  height: 600px;
  width: 100%;
  background-color: white;
  border-radius: 4px;
  box-sizing: border-box;
  padding: 5px;
}
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.chart {
  height: 400px;
}
</style>