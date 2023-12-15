<script setup>
import {Delete, Edit, EditPen, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";


const userList = ref([])

const addUserDialog = ref(false)
const updateUserDialog = ref(false)

const ruleForm = ref()
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
  affiliatedSchool: '',
  qualification: '',
  researchDirection: ''
})
const updateUserForm = reactive({
  id: '',
  username: '',
  netId: '',
  address: '',
  code: '',
  identificationCode: '',
  birthday: '',
  gender: '',
  entryTime: '',
  finishTime: '',
  affiliatedSchool: '',
  qualification: '',
  researchDirection: ''
})

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

  ],
  qualification: [

  ],
  researchDirection: [

  ]
})

const closeAddDialog = () =>{
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
}

const closeUpdateDialog = () =>{
  updateUserDialog.value = false
}
const openUpdateDialog = (row) =>{
  updateUserDialog.value = true

  updateUserForm.id = row.id
  updateUserForm.username = row.username
  updateUserForm.netId = row.netId
  updateUserForm.address = row.address
  updateUserForm.code = row.code
  updateUserForm.identificationCode = row.identificationCode
  updateUserForm.gender = row.gender
  updateUserForm.birthday = row.birthday
  updateUserForm.entryTime = row.entryTime
  updateUserForm.finishTime = row.finishTime
  updateUserForm.affiliatedSchool = row.affiliatedSchool
  updateUserForm.qualification = row.qualification
  updateUserForm.researchDirection = row.researchDirection
}

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/teacher/insertUser' , addUserForm).then(res =>{
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

const updateForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/teacher/updateUserById', updateUserForm).then(res =>{
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



const select = ref('1')

const selectFrom = reactive({
  username: '',
  netId: ''
})

const getList = () => {
  request.get("/teacher/listTeacher?username="+selectFrom.username).then(res => {
    if(res.code === 200){
      userList.value = res.data
      console.log(res.data)
    } else {
      ElMessage.error(res.message)
    }
  })
}

const reset = () =>{
  selectFrom.netId = ''
  selectFrom.username = ''
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
})

function formatDate(row ,col){
  let data = row[col.property]
  if(data == null)
    return null
  let date_time = new Date(data);
  return date_time.toLocaleDateString()
}
</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-input v-model="selectFrom.username" placeholder="请输入" class="input-with-select" style="height: 30px; width: 300px">
          <template #append>
            <el-select v-model="select" placeholder="Select" style="width: 100px">
              <el-option label="姓名" value="1" />
              <el-option label="Net Id" value="2" />
            </el-select>
          </template>
        </el-input>
        <el-button @click="getList" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
      </div>
      <div style="height: 15%; width: 100%"/>
      <div style="width: 100%; height: 50%; display: flex">

        <el-button @click="reset" type="danger" plain><el-icon style="margin-right: 3px"><Refresh /></el-icon>重置</el-button>
        <el-button type="success" plain @click="addUserDialog = true"><el-icon style="margin-right: 3px"><Plus /></el-icon>新增</el-button>
      </div>
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="userList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="netId" label="Net Id" width="80"/>
        <el-table-column prop="username" label="姓名" width="100"/>
        <el-table-column prop="code" label="学号" width="60"/>
        <el-table-column prop="identificationCode" label="身份证" width="160"/>
        <el-table-column prop="address" label="住址" width="120"/>
        <el-table-column prop="gender" label="性别" width="60"/>
        <el-table-column prop="affiliatedSchool" label="所属学院" width="120"/>
        <el-table-column prop="qualification" label="职称" width="80"/>
        <el-table-column prop="researchDirection" label="研究方向" width="100"/>
        <el-table-column prop="birthday" label="生日" width="100" :formatter="formatDate"/>
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

  <!--新建用户面板-->
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
        <el-form-item label="所属学院" prop="affiliatedSchool">
          <el-input v-model="addUserForm.affiliatedSchool" />
        </el-form-item>
        <el-form-item label="职称" prop="qualification">
          <el-input v-model="addUserForm.qualification" />
        </el-form-item>
        <el-form-item label="研究方向" prop="researchDirection">
          <el-input v-model="addUserForm.researchDirection" />
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

        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleForm)">新建</el-button>
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
        <el-form-item label="用户名" prop="username">
          <el-input v-model="updateUserForm.username" />
        </el-form-item>
        <el-form-item label="Net Id" prop="netId">
          <el-input v-model="updateUserForm.netId" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="updateUserForm.address" />
        </el-form-item>
        <el-form-item label="学号" prop="code">
          <el-input v-model="updateUserForm.code" />
        </el-form-item>
        <el-form-item label="身份证" prop="identificationCode">
          <el-input v-model="updateUserForm.identificationCode" />
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker
              v-model="updateUserForm.birthday"
              type="date"
              placeholder="请选择生日"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="updateUserForm.gender" placeholder="请选择您的性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
            <el-option label="未知" value="未知" />
          </el-select>
        </el-form-item>
        <el-form-item label="所属学院" prop="affiliatedSchool">
          <el-input v-model="updateUserForm.affiliatedSchool" />
        </el-form-item>
        <el-form-item label="职称" prop="qualification">
          <el-input v-model="updateUserForm.qualification" />
        </el-form-item>
        <el-form-item label="研究方向" prop="researchDirection">
          <el-input v-model="updateUserForm.researchDirection" />
        </el-form-item>
        <el-form-item label="入学日期" prop="entryTime">
          <el-date-picker
              v-model="updateUserForm.entryTime"
              type="date"
              placeholder="请选择入学日期"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="毕业日期" prop="finishTime">
          <el-date-picker
              v-model="updateUserForm.finishTime"
              type="date"
              placeholder="请选择毕业日期"
              style="width: 100%"
          />
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