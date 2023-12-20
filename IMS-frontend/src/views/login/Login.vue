<script setup>
import {onMounted, reactive, ref, watch} from "vue";
import Title from "@/components/Title.vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useInfoStore} from "@/stores/pinna"


const login = () =>{
  request.post("/login", loginForm).then(res =>{
    if(res.code === 200){
      localStorage.clear()
      localStorage.setItem("user", JSON.stringify(res.data))
      getScoreList(res.data.id)
      ElMessage.success("登录成功")
      if(res.data.level === 1)
        router.push('/admin')
      if(res.data.level === 2)
        router.push('/teacher')
      if(res.data.level === 3)
        router.push('/student')
    } else {
      ElMessage.error(res.message)
    }
  })
}

function getScoreList(id){
  request.get('/score/getScoreCountByUserId?userId='+id).then(res=>{
    if(res.code === 200){
      localStorage.setItem("score", JSON.stringify(res.data))
      console.log(res.data)
    } else {
      console.log("get score error")
    }
  })
}

const schoolList = ref([])
function getSchoolList(){
  request.get('/school/getAllSchool').then(res=>{
    if(res.code === 200){
      schoolList.value = res.data
    } else {
      console.log(res.message)
    }
  })
}

onMounted(()=>{
  getSchoolList()
})

const loginForm = reactive({
  username: '',
  password: ''
})

//注册界面
const addUserForm = reactive({
  username: '',
  netId: '',
  password: '',
  address: '',
  code: '',
  identificationCode: '',
  birthday: '',
  gender: '',
  entryTime: '',
  finishTime: '',
  level: '',
  affiliatedSchool: '',
  qualification: '',
  researchDirection: '',
  totalCredit: '',
})
const addUserDialog = ref(false)
const ruleForm = ref()
const rules = reactive({
  username: [
    { required: true, message: '用户名不能为空', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    { min: 6, message: '密码长度必须大于6位'}
  ],
  identificationCode: [
    { required: true, message: '身份证不能为空', trigger: 'blur' },
    { min: 18, max: 18, message: '身份证长度必须为18位'}
  ],
  netId: [
    { required: true, message: 'NetId不能为空', trigger: 'blur' }
  ],
  address: [
    { required: true, message: '地址不能为空', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '学号不能为空', trigger: 'blur' }
  ],
  birthday: [
    { required: true, message: '出生日期不能为空', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '性别不能为空', trigger: 'blur' }
  ],
  entryTime: [
    { required: true, message: '入学日期不能为空', trigger: 'blur' }
  ],
  finishTime: [
    { required: true, message: '离校日期不能为空', trigger: 'blur' }
  ],
  affiliatedSchool: [
    { required: true, message: '所属学院不能为空', trigger: 'blur' }
  ],
  level: [
    { required: true, message: '等级权限不能为空', trigger: 'blur' }
  ],
  totalCredit: [
    { required: true, message: '总学分不能为空', trigger: 'blur' },
    {
      transform(value) {
        return Number(value);
      },
      validator(rule, value, callback) {
        if (Number.isFinite(value) && value > 0) {
          callback();
        } else {
          callback(new Error("请输入大于0的数字"));
        }
      },
      trigger: "blur",
    }
  ]
})

function closeAddDialog(){
  addUserDialog.value = false
  addUserForm.username = ''
  addUserForm.netId = ''
  addUserForm.password = ''
  addUserForm.address = ''
  addUserForm.gender = ''
  addUserForm.code = ''
  addUserForm.identificationCode = ''
  addUserForm.birthday = ''
  addUserForm.entryTime = ''
  addUserForm.finishTime = ''
  addUserForm.affiliatedSchool = ''
  addUserForm.qualification = ''
  addUserForm.researchDirection = ''
  addUserForm.level = ''
  addUserForm.totalCredit = ''
}

const SignUp = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/signUp' , addUserForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('添加成功')
          closeAddDialog()
        } else {
          ElMessage.error('添加失败')
          console.log(res.message)
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

function openSignUpDialog(){
  addUserDialog.value = true
}

//帮助界面
const helpDialog = ref(false)
function openHelpDialog(){
  helpDialog.value = true
}
function closeHelpDialog(){
  helpDialog.value = false
}

</script>

<template>
  <div class="main-wrapper">
    <Title/>
    <div class="wrapper">
      <div class="common-layout">
        <el-container>
          <el-header class="header" height="130px">
            <div>
              <img class="casimg" src="../../../public/caslogo.png" alt="caslogo">
            </div>
          </el-header>
          <el-main class="main">
            <el-input class="inputID" v-model="loginForm.username" placeholder="NetId" />
            <el-input class="inputPW" v-model="loginForm.password" placeholder="密 码" show-password/>
            <el-button class="loginBtn" type="success" @click="login">登 录</el-button>
            <el-divider/>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-button class="Btn" type="primary" @click="openSignUpDialog">注 册</el-button>
              </el-col>
              <el-col :span="12">
                <el-button class="Btn" type="primary" @click="openHelpDialog">帮 助</el-button>
              </el-col>
            </el-row>
          </el-main>
        </el-container>
      </div>
    </div>
  </div>

  <!--注册Dialog-->
  <el-dialog
      v-model="addUserDialog"
      title="新增用户信息"
      width="30%"
      :before-close="closeAddDialog"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="addUserForm" label-width="120px" :rules="rules" ref="ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addUserForm.username" />
        </el-form-item>
        <el-form-item label="Net Id" prop="netId">
          <el-input v-model="addUserForm.netId" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addUserForm.password" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="addUserForm.address" />
        </el-form-item>
        <el-form-item label="学号" prop="code">
          <el-input v-model="addUserForm.code" />
        </el-form-item>
        <el-form-item label="身份证" prop="identificationCode">
          <el-input v-model="addUserForm.identificationCode" />
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker
              v-model="addUserForm.birthday"
              type="date"
              placeholder="请选择生日"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="addUserForm.gender" placeholder="请选择您的性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
            <el-option label="未知" value="未知" />
          </el-select>
        </el-form-item>
        <el-form-item label="总学分" prop="totalCredit">
          <el-input v-model="addUserForm.totalCredit" />
        </el-form-item>
        <el-form-item label="入学日期" prop="entryTime">
          <el-date-picker
              v-model="addUserForm.entryTime"
              type="date"
              placeholder="请选择入学日期"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="毕业日期" prop="finishTime">
          <el-date-picker
              v-model="addUserForm.finishTime"
              type="date"
              placeholder="请选择毕业日期"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="所属学院" prop="affiliatedSchool">
          <el-select v-model="addUserForm.affiliatedSchool" class="m-2" placeholder="Select">
            <el-option
                v-for="item in schoolList"
                :key="item.id"
                :label="item.name"
                :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="用户等级" prop="level">
          <el-select v-model="addUserForm.level" placeholder="请选择您的用户等级(这也太op了)"> <!-- 为便于测试，可随意选择用户等级 -->
            <el-option label="管理员" value="1" />
            <el-option label="教师" value="2" />
            <el-option label="学生" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="SignUp(ruleForm)">新建</el-button>
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

  <el-dialog
      v-model="helpDialog"
      title="帮助界面"
      width="30%"
      :before-close="closeHelpDialog"
      style="border-radius: 8px"
  >
    <p>您遇到什么问题了吗喵?</p>
    <p>但是本界面还未完成x</p>
    <p>忘记密码还请重新注册x</p>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeHelpDialog">取消</el-button>
        <el-button type="primary" @click="closeHelpDialog">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.header{
  background-color: rgb(48,96,60);
  border-radius: 10px;
}
.casimg{
  margin-top: 20px;
}
.inputPW{
  margin-top: 10px;
}
.loginBtn{
  width: 100%;
  margin-top: 20px;
}
.common-layout{
  background-color: aliceblue;
  margin-top: 30px;
  border-radius: 10px;
}
.wrapper{
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.Btn{
  width: 100%;
}
.main-wrapper{
  margin-top: 60px;
}

.el-card{
  background-color: aliceblue;
}
</style>