<script setup lang="ts">
// ===================== 逻辑部分 =====================
// 引入 Vue 的 ref 函数，用于创建响应式数据
import { ref } from 'vue'
// 引入 Element Plus 的消息提示组件
import { ElMessage } from 'element-plus'
// 引入登录 API 函数
import { login, saveLoginInfo } from '@/api/auth'

// 定义一个响应式对象 loginForm，用来存储表单输入的数据
// ref 让数据变化时页面自动更新
const loginForm = ref({
  username: '', // 用户名输入框绑定的数据
  password: ''  // 密码输入框绑定的数据
})

// 定义登录按钮的加载状态
const loading = ref(false)

// 定义登录按钮点击时要执行的函数
// 现在会真正调用后端登录接口
async function onLogin() {
  // 前端校验：检查用户名和密码是否都填写了
  if (!loginForm.value.username || !loginForm.value.password) {
    ElMessage.error('请输入用户名和密码')
    return
  }

  // 设置加载状态，防止重复点击
  loading.value = true

  try {
    // 调用后端登录接口
    const response = await login({
      username: loginForm.value.username,
      password: loginForm.value.password
    })

    // 根据后端返回的状态码判断登录是否成功
    if (response.code === 0) {
      // 登录成功
      ElMessage.success('登录成功！')
      
      // 保存登录信息到本地存储（如果有token的话）
      if (response.data) {
        saveLoginInfo(response.data)
      }
      
      // 这里可以跳转到主页面或其他页面
      // 例如：router.push('/dashboard')
      console.log('登录成功，用户信息：', response.data)
      
    } else {
      // 登录失败，显示后端返回的错误信息
      ElMessage.error(response.message || '登录失败')
    }
  } catch (error) {
    // 处理异常情况
    console.error('登录异常：', error)
    ElMessage.error('登录过程中发生错误，请稍后重试')
  } finally {
    // 无论成功还是失败，都要关闭加载状态
    loading.value = false
  }
}
</script>

<template>
  <!-- ===================== 结构和样式部分 ===================== -->
  <!-- 外层容器：让内容垂直水平居中，背景色全屏 -->
  <div class="login-bg">
    <!-- 登录卡片：加大宽度，适合一行欢迎语，带阴影和圆角 -->
    <el-card class="login-card">
      <!-- 标题：加粗、字号适中、居中、单行显示，防止换行 -->
      <h2 class="login-title">欢迎使用火车票管理系统</h2>
      <!-- 登录表单，label-width 让输入框对齐 -->
      <el-form :model="loginForm" @submit.prevent="onLogin" label-width="80px">
        <!-- 用户名输入框：clearable 可一键清空，体验更好 -->
        <el-form-item label="用户名">
          <el-input v-model="loginForm.username" placeholder="请输入用户名" clearable />
        </el-form-item>
        <!-- 密码输入框：show-password 可切换明文/密文，clearable 可清空 -->
        <el-form-item label="密码">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password clearable />
        </el-form-item>
        <!-- 登录按钮：宽度100%，圆角更美观，易于点击，带加载状态 -->
        <el-form-item>
          <el-button 
            type="primary" 
            @click="onLogin" 
            class="login-btn"
            :loading="loading"
            :disabled="loading"
          >
            {{ loading ? '登录中...' : '登录' }}
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
/* ===================== 页面整体美化 ===================== */
/* 让背景色全屏且无横向滚动条 */
.login-bg {
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(120deg, #e0eafc 0%, #cfdef3 100%);
  /* 全屏样式已在 main.css 中定义，这里只负责布局 */
}

/* 登录卡片样式：加大宽度，阴影、圆角、宽度适中 */
.login-card {
  box-shadow: 0 4px 24px rgba(0,0,0,0.12); /* 阴影效果，让卡片有层次感 */
  border-radius: 16px; /* 圆角 */
  width: 500px; /* 加大宽度，适合一行欢迎语 */
  padding: 32px 32px 24px 32px; /* 上、右、下、左的内边距 */
  background: #fff;
  position: relative; /* 确保卡片在背景之上 */
  z-index: 10; /* 层级比背景高 */
}

/* 标题样式：居中、加粗、单行显示、字号适中 */
.login-title {
  text-align: center; /* 居中 */
  font-weight: bold; /* 加粗 */
  font-size: 1.4rem; /* 字号适中，防止过大换行 */
  margin-bottom: 24px;
  color: #34495e;
  letter-spacing: 2px;
  white-space: nowrap; /* 强制单行显示，不换行 */
  overflow: hidden;    /* 超出部分隐藏 */
  text-overflow: ellipsis; /* 超出部分显示省略号 */
}

/* 登录按钮样式：宽度100%，圆角 */
.login-btn {
  width: 100%; /* 按钮宽度占满表单 */
  border-radius: 8px; /* 圆角 */
  font-size: 0.8rem; /* 字号稍大 */
  letter-spacing: 1px; /* 字母间距 */
}
</style>
