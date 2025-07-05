<script setup lang="ts">
// 引入 Vue 的 ref 函数，用于创建响应式数据（数据变化时页面会自动更新）
import { ref } from 'vue'

// 定义一个响应式对象 loginForm，用来存储表单输入的数据（用户名和密码）
const loginForm = ref({
  username: '', // 用户名输入框绑定的数据，初始为空
  password: ''  // 密码输入框绑定的数据，初始为空
})

// 定义登录按钮点击时要执行的函数
function onLogin() {
  // 检查用户名和密码是否都填写了
  if (loginForm.value.username && loginForm.value.password) {
    // 如果都填写了，弹出登录成功（这里只是前端演示，后续可以对接后端接口）
    alert('登录成功！（这里只是前端演示）')
  } else {
    // 如果有一项没填，弹出提示
    alert('请输入用户名和密码')
  }
}
</script>

<template>
  <!-- 外层 div，用于让登录框居中显示，最大宽度 400px，距离顶部 100px -->
  <div style="max-width: 400px; margin: 100px auto;">
    <!-- Element Plus 的卡片组件，让内容有白色背景和阴影 -->
    <el-card>
      <!-- 页面标题，居中显示 -->
      <h2 style="text-align:center;">欢迎使用火车票管理系统</h2>
      <!-- Element Plus 的表单组件，:model 绑定表单数据，@submit.prevent 阻止默认提交并执行 onLogin -->
      <el-form :model="loginForm" @submit.prevent="onLogin">
        <!-- 表单项：用户名 -->
        <el-form-item label="用户名">
          <!-- 输入框，v-model 双向绑定 loginForm.username，placeholder 是提示文字 -->
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <!-- 表单项：密码 -->
        <el-form-item label="密码">
          <!-- 密码输入框，v-model 绑定 loginForm.password，type="password" 隐藏输入内容 -->
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <!-- 表单项：登录按钮 -->
        <el-form-item>
          <!-- Element Plus 的主按钮，点击时执行 onLogin -->
          <el-button type="primary" @click="onLogin">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
