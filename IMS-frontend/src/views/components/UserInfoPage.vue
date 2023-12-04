<script setup>
import {Delete, Edit, EditPen, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const userList = ref([])

const select = ref('1')

const selectFrom = reactive({
  username: '',
  netId: ''
})

const getList = () => {
  request.get("/user/listUser?username="+selectFrom.username).then(res => {
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
  request.delete("/user/deleteUserById/" + id).then(res => {
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
        <el-button type="success" plain><el-icon style="margin-right: 3px"><Plus /></el-icon>新增</el-button>
      </div>
    </div>
    <div style="height: 5%; width: 100%"/>
    <el-scrollbar class="table-wrapper">
      <el-table :data="userList" stripe style="width: 100%" border>
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="netId" label="Net Id" width="100"/>
        <el-table-column prop="username" label="姓名" width="100"/>
        <el-table-column prop="code" label="学号" width="100"/>
        <el-table-column prop="address" label="住址" width="180"/>
        <el-table-column prop="gender" label="性别" width="60"/>
        <el-table-column prop="birthday" label="生日" width="180"/>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" plain><el-icon style="margin-right: 5px"><Edit /></el-icon>修改</el-button>
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