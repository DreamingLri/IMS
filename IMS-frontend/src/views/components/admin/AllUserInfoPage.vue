<script setup>
import {Delete, Edit, EditPen, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";


const userList = ref([])

const updateUserDialog = ref(false)
const schoolList = ref([])
let user = JSON.parse(localStorage.getItem("user"))
function getSchoolList(){
  request.get('/school/getAllSchool').then(res=>{
    if(res.code === 200){
      schoolList.value = res.data
    } else {
      console.log(res.message)
    }
  })
}

const ruleForm = ref()
const updateUserForm = reactive({
  userId: '',
  levelId: ''
})

const rules = reactive({
  level: [

  ]
})


const closeUpdateDialog = () =>{
  updateUserDialog.value = false
}
const openUpdateDialog = (row) =>{
  updateUserDialog.value = true

  updateUserForm.userId = row.id
  // updateUserForm.levelId = formatLevel(row.level)
}


const updateForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/user-level/updateLevelByUserId', updateUserForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('更新成功')
          closeUpdateDialog()
          reset()
        } else {
          ElMessage.error('更新失败')
          console.log(res.message)
          console.log(updateUserForm)
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




const username = ref('')

const getList = () => {
  request.get("/user-level/getUserWithLevel?username="+username.value+"&userId="+user.id).then(res => {
    if(res.code === 200){
      userList.value = res.data
      console.log(res.data)
    } else {
      ElMessage.error(res.message)
    }
  })
}

const reset = () =>{
  username.value = ''
  getList()
}

const removeUser = (id) =>{
  request.delete("/teacher/deleteUserById/" + id).then(res => {
    if(res.code === 200){
      ElMessage.success("删除成功")
      reset()
    } else {
      ElMessage.error(res.message)
    }
  })
}

onMounted(() => {
  getList()
  getSchoolList()
})

function formatDate(row ,col){
  let data = row[col.property]
  if(data == null)
    return null
  let date_time = new Date(data);
  return date_time.toLocaleDateString()
}

function formatLevel(data){
  if(data === 1)
    return "管理员"
  if(data === 2)
    return "教师"
  if(data === 3)
    return "学生"
}

function filterLevel(value, row){
  return row.level === value
}

function formatType(type){
  if(type === 1)
    return "warning"
  if(type === 2)
    return "success"
  if(type === 3)
    return ""
}
</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-input v-model="username" placeholder="请输入" class="input-with-select" style="height: 30px; width: 300px">
          <template #append>
            姓名
          </template>
        </el-input>
        <el-button @click="getList" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
        <el-button @click="reset" type="danger" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Refresh /></el-icon>重置</el-button>
      </div>
      <div style="height: 15%; width: 100%"/>
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="userList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="netId" label="Net Id" width="80"/>
        <el-table-column prop="username" label="姓名" width="100"/>
        <el-table-column prop="code" label="学号" width="100"/>
        <el-table-column prop="gender" label="性别" width="60"/>
        <el-table-column prop="affiliatedSchool" label="所属学院" width="120"/>
        <el-table-column
            prop="tag"
            label="等级"
            width="100"
            :formatter="formatLevel"
            :filters="[
        { text: '管理员', value: 1 },
        { text: '老师', value: 2 },
        { text: '学生', value: 3 },
      ]"
            :filter-method="filterLevel"
            filter-placement="bottom-end"

        >
          <template #default="scope">
            <el-tag
                :type="formatType(scope.row.level)"
                disable-transitions
            >{{ formatLevel(scope.row.level) }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="entryTime" label="入学日期" width="100" :formatter="formatDate"/>
        <el-table-column prop="finishTime" label="离校日期" width="100" :formatter="formatDate"/>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="openUpdateDialog(scope.row)"><el-icon style="margin-right: 5px"><Edit /></el-icon>修改</el-button>
            <el-popconfirm title="你确定要删除这个用户吗?" @confirm="removeUser(scope.row.id)" confirm-button-text="确定" cancel-button-text="取消">
              <template #reference>
                <el-button type="danger" plain><el-icon style="margin-right: 5px"><Delete /></el-icon>删除</el-button>
              </template>
            </el-popconfirm>

          </template>
        </el-table-column>
      </el-table>
    </el-scrollbar>
  </div>


  <!--更新用户面板-->
  <el-dialog
      v-model="updateUserDialog"
      title="更新用户信息"
      width="30%"
      :before-close="closeUpdateDialog"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="updateUserForm" label-width="120px" :rules="rules" ref="ruleForm">

        <el-form-item label="权限设置" prop="level">
          <el-select v-model="updateUserForm.levelId" placeholder="请选择要更新的等级">
            <el-option label="管理员" value= 1 />
            <el-option label="教师" value= 2 />
            <el-option label="学生" value= 3 />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="updateForm(ruleForm)">更新</el-button>
          <el-button @click="resetForm(ruleForm)">重置</el-button>
        </el-form-item>

      </el-form>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeUpdateDialog">取消</el-button>
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
  height: 10%;
}
.table-wrapper{
  height: 85%;
  width: 100%;
}
</style>