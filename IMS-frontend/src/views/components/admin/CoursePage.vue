<script setup>
import {Delete, Edit, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";


const userList = ref([])

const addCourseDialog = ref(false)
const updateCourseDialog = ref(false)
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


const ruleForm = ref()
const addCourseForm = reactive({
  id: '',
  name: '',
  openedBy: '',
  credit: '',
  studentNumber: '',
  startTime: '',
  endTime: '',
  teacher: '',
  period: '',
  place: '',
  courseAssessment: ''
})
const updateCourseForm = reactive({
  id: '',
  name: '',
  openedBy: '',
  credit: '',
  studentNumber: '',
  startTime: '',
  endTime: '',
  teacher: '',
  period: '',
  place: '',
  courseAssessment: ''
})

const rules = reactive({
  // name: [
  //   { required: true, message: '课程名不能为空', trigger: 'blur' },
  // ],
  // openedBy: [
  //   { required: true, message: '开课学院不能为空', trigger: 'blur' },
  // ],
  // credit: [
  //   { required: true, message: '学分不能为空', trigger: 'blur' },
  // ],
  // studentNumber: [
  //   { required: true, message: '开班人数不能为空', trigger: 'blur' },
  // ],
  // startTime: [
  //   { required: true, message: '开课时间不能为空', trigger: 'blur' },
  // ],
  // teacher: [
  //   { required: true, message: '开课老师不能为空', trigger: 'blur' },
  // ],
  // period: [
  //   { required: true, message: '学时不能为空', trigger: 'blur' },
  // ],
  // place: [
  //   { required: true, message: '上课地点不能为空', trigger: 'blur' },
  // ],
  // courseAssessment: [
  //   { required: true, message: '考核方式不能为空', trigger: 'blur' },
  // ]
})

const closeAddDialog = () =>{
  addCourseDialog.value = false

  addCourseForm.name = ''
  addCourseForm.openedBy = ''
  addCourseForm.credit = ''
  addCourseForm.studentNumber = ''
  addCourseForm.startTime = ''
  addCourseForm.endTime = ''
  addCourseForm.teacher = ''
  addCourseForm.period = ''
  addCourseForm.place = ''
  addCourseForm.courseAssessment = ''
}

const closeUpdateDialog = () =>{
  updateCourseDialog.value = false
}
const openUpdateDialog = (row) =>{
  updateCourseDialog.value = true

  updateCourseForm.id = row.id
  updateCourseForm.name = row.name
  updateCourseForm.credit = row.credit
  updateCourseForm.openedBy = row.openedBy
  updateCourseForm.studentNumber = row.studentNumber
  updateCourseForm.startTime = row.startTime
  updateCourseForm.endTime = row.endTime
  updateCourseForm.teacher = row.teacher
  updateCourseForm.period = row.period
  updateCourseForm.place = row.place
  updateCourseForm.courseAssessment = row.courseAssessment
}

const submitForm = async (formEl) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post('/course/insertCourse' , addCourseForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('添加成功')
          closeAddDialog()
          reset()
        } else {
          ElMessage.error(res.message)
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
      request.post('/course/updateCourseById', updateCourseForm).then(res =>{
        if(res.code === 200){
          ElMessage.success('更新成功')
          closeUpdateDialog()
          reset()
        } else {
          ElMessage.error(res.message)
          console.log(updateCourseForm)
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


const selectFrom = reactive({
  name: ''
})

const name = ref('')

const getList = () => {
  request.get("/course/listCourse?name="+encodeURIComponent(name.value)).then(res => {
    if(res.code === 200){
      userList.value = res.data
      console.log(res.data)
    } else {
      ElMessage.error(res.message)
    }
  })
}

const teacherList = ref([])
const getTeacherList = () =>{
  request.get("/teacher/listTeacherName").then(res => {
    if(res.code === 200){
      teacherList.value = res.data
      console.log(teacherList.value)
    } else {
      ElMessage.error("listTeacherError")
    }
  })
}

const reset = () =>{
  name.value = ''
  getList()
  getTeacherList()
}

const removeCourse = (id) =>{
  request.delete("/course/deleteCourseById/" + id).then(res => {
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
  getTeacherList()
  getSchoolList()
})

function formatDate(row ,col){
  let data = row[col.property]
  if(data == null)
    return null
  let date_time = new Date(data);
  return date_time.toLocaleDateString()
}

const courseTime = ref([]);
</script>

<template>
  <div class="main-wrapper">
    <div class="header-wrapper">
      <div style="width: 100%; height: 40%; display: flex">
        <el-input v-model="name" placeholder="请输入" class="input-with-select" style="height: 30px; width: 300px"></el-input>
        <el-button @click="getList" type="primary" plain style="margin-left: 10px; height: 30px"><el-icon style="margin-right: 3px"><Search /></el-icon>搜索</el-button>
      </div>
      <div style="height: 15%; width: 100%"/>
      <div style="width: 100%; height: 50%; display: flex">

        <el-button @click="reset" type="danger" plain><el-icon style="margin-right: 3px"><Refresh /></el-icon>重置</el-button>
        <el-button type="success" plain @click="addCourseDialog = true"><el-icon style="margin-right: 3px"><Plus /></el-icon>新增</el-button>
      </div>
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="userList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="name" label="课程名称" width="130" />
        <el-table-column prop="openedBy" label="开课学院" width="130" />
        <el-table-column prop="teacher" label="开课老师" width="130" />
        <el-table-column prop="place" label="上课地点" width="100"/>
        <el-table-column prop="credit" label="学分" width="70" />
        <el-table-column prop="studentNumber" label="开课人数" width="100" />
        <el-table-column prop="period" label="学时" width="100" />
        <el-table-column prop="courseAssessment" label="考核方式" width="150" />
        <el-table-column prop="startTime" label="开始日期" width="100" :formatter="formatDate"/>
        <el-table-column prop="endTime" label="结束日期" width="100" :formatter="formatDate"/>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain @click="openUpdateDialog(scope.row)"><el-icon style="margin-right: 5px"><Edit /></el-icon>修改</el-button>
            <el-popconfirm title="你确定要删除这个课程吗?" @confirm="removeCourse(scope.row.id)" confirm-button-text="确定" cancel-button-text="取消">
              <template #reference>
                <el-button type="danger" plain><el-icon style="margin-right: 5px"><Delete /></el-icon>删除</el-button>
              </template>
            </el-popconfirm>

          </template>
        </el-table-column>
      </el-table>
    </el-scrollbar>
  </div>

  <!--新建课程面板-->
  <el-dialog
      v-model="addCourseDialog"
      title="新增课程信息"
      width="30%"
      :before-close="closeAddDialog"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="addCourseForm" label-width="120px" :rules="rules" ref="ruleForm">
        <el-form-item label="课程名" prop="name">
          <el-input v-model="addCourseForm.name" />
        </el-form-item>
        <el-form-item label="开课学院" prop="openedBy">
          <el-select v-model="addCourseForm.openedBy" class="m-2" placeholder="请选择开课学院">
            <el-option
                v-for="item in schoolList"
                :key="item.id"
                :label="item.name"
                :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="开课老师" prop="teacher">
          <el-select v-model="addCourseForm.teacher" class="m-2" placeholder="请选择开课老师">
            <el-option
                v-for="item in teacherList"
                :key="item.value"
                :label="item.username"
                :value="item.username"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="上课地点" prop="place">
          <el-input v-model="addCourseForm.place" />
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input v-model="addCourseForm.credit" />
        </el-form-item>
        <el-form-item label="学时" prop="period">
          <el-input v-model="addCourseForm.period" />
        </el-form-item>
        <el-form-item label="学生人数" prop="studentNumber">
          <el-input v-model="addCourseForm.studentNumber" />
        </el-form-item>
        <el-form-item label="开课时间" prop="startTime">
          <el-col :span="11">
            <el-date-picker
                v-model="addCourseForm.startTime"
                type="date"
                placeholder="开始时间"
                style="width: 100%"
            />
          </el-col>
          <el-col :span="2" class="text-center">
            <span class="text-gray-500">-</span>
          </el-col>
          <el-col :span="11">
            <el-date-picker
                v-model="addCourseForm.endTime"
                placeholder="结束时间"
                style="width: 100%"
            />
          </el-col>
        </el-form-item>

        <el-form-item label="考核方式" prop="courseAssessment">
          <el-select v-model="addCourseForm.courseAssessment" placeholder="请选择考核方式">
            <el-option label="开卷考试" value="开卷考试" />
            <el-option label="闭卷考试" value="闭卷考试" />
            <el-option label="论文" value="论文" />
            <el-option label="其它" value="其它" />
          </el-select>
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

  <!--更新课程面板-->
  <el-dialog
      v-model="updateCourseDialog"
      title="更新课程信息"
      width="30%"
      :before-close="closeUpdateDialog"
      style="border-radius: 8px"
  >
    <div style="margin-right: 20%">
      <el-form :model="addCourseForm" label-width="120px" :rules="rules" ref="ruleForm">
        <el-form-item label="课程名" prop="name">
          <el-input v-model="updateCourseForm.name" />
        </el-form-item>
        <el-form-item label="开课学院" prop="openedBy">
          <el-select v-model="updateCourseForm.openedBy" class="m-2" placeholder="请选择开课学院">
            <el-option
                v-for="item in schoolList"
                :key="item.id"
                :label="item.name"
                :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="开课老师" prop="teacher">
          <el-select v-model="updateCourseForm.teacher" class="m-2" placeholder="请选择开课老师">
            <el-option
                v-for="item in teacherList"
                :key="item.id"
                :label="item.username"
                :value="item.username"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="上课地点" prop="place">
          <el-input v-model="updateCourseForm.place" />
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input v-model="updateCourseForm.credit" />
        </el-form-item>
        <el-form-item label="学时" prop="name">
          <el-input v-model="updateCourseForm.period" />
        </el-form-item>
        <el-form-item label="学生人数" prop="studentNumber">
          <el-input v-model="updateCourseForm.studentNumber" />
        </el-form-item>
        <el-form-item label="开课时间" prop="startTime">
          <el-col :span="11">
            <el-date-picker
                v-model="updateCourseForm.startTime"
                type="date"
                placeholder="开始时间"
                style="width: 100%"
            />
          </el-col>
          <el-col :span="2" class="text-center">
            <span class="text-gray-500">-</span>
          </el-col>
          <el-col :span="11">
            <el-date-picker
                v-model="updateCourseForm.endTime"
                placeholder="结束时间"
                style="width: 100%"
            />
          </el-col>
        </el-form-item>

        <el-form-item label="考核方式" prop="courseAssessment">
          <el-select v-model="updateCourseForm.courseAssessment" placeholder="请选择考核方式">
            <el-option label="开卷考试" value="开卷考试" />
            <el-option label="闭卷考试" value="闭卷考试" />
            <el-option label="论文" value="论文" />
            <el-option label="其它" value="其它" />
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