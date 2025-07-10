<template>
  <div class="ticket-upload-container">
    <!-- 左侧：图片上传与预览 -->
    <div class="left-panel">
      <!-- 上传组件，限制图片格式和大小 -->
      <el-upload
        class="upload-demo"
        drag
        action=""
        :before-upload="handleBeforeUpload"
        :show-file-list="false"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">拖拽或点击上传火车票照片</div>
      </el-upload>
      <!-- 图片预览 -->
      <div v-if="imageUrl" class="image-preview">
        <img :src="imageUrl" alt="火车票照片" />
      </div>
      <!-- 上传按钮 -->
      <el-button type="primary" @click="triggerUpload" :disabled="!rawFile">上传照片</el-button>
    </div>

    <!-- 右侧：识别结果表单 -->
    <div class="right-panel">
      <!-- 加载动画 -->
      <el-spin v-if="loading" tip="正在识别，请稍候..." />
      <!-- 识别结果表单 -->
      <el-form :model="form" label-width="80px">
        <el-row :gutter="24">
          <!-- 左列 -->
          <el-col :span="12">
            <el-form-item label="乘客ID">
              <el-select v-model="form.passenger_id" placeholder="请选择乘客ID">
                <el-option v-for="p in passengerList" :key="p.id" :label="p.id + ' - ' + p.real_name" :value="p.id" />
              </el-select>
            </el-form-item>
            <el-form-item label="票号">
              <el-input v-model="form.ticket_id" />
            </el-form-item>
            <el-form-item label="车次号">
              <el-input v-model="form.train_number" />
            </el-form-item>
            <el-form-item label="出发站">
              <el-input v-model="form.departure_station_code" />
            </el-form-item>
            <el-form-item label="到达站">
              <el-input v-model="form.arrival_station_code" />
            </el-form-item>
            <el-form-item label="出发日期">
              <el-date-picker v-model="form.departure_date" type="date" placeholder="选择日期" style="width: 100%;" />
            </el-form-item>
            <el-form-item label="发车时间">
              <el-time-picker v-model="form.departure_time" placeholder="选择时间" style="width: 100%;" />
            </el-form-item>
          </el-col>
          <!-- 右列 -->
          <el-col :span="12">
            <el-form-item label="座位号">
              <el-input v-model="form.seat_number" />
            </el-form-item>
            <el-form-item label="车厢号">
              <el-input v-model="form.carriage_number" />
            </el-form-item>
            <el-form-item label="席别">
              <el-select v-model="form.seat_type" placeholder="请选择席别">
                <el-option v-for="type in seatTypeList" :key="type.value" :label="type.label" :value="type.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="票价">
              <el-input v-model="form.ticket_price" />
            </el-form-item>
            <el-form-item label="票状态">
              <el-select v-model="form.ticket_status" placeholder="请选择票状态">
                <el-option label="未使用" value="unused" />
                <el-option label="已使用" value="used" />
                <el-option label="已退票" value="refunded" />
                <el-option label="已改签" value="changed" />
              </el-select>
            </el-form-item>
            <el-form-item label="到达时间">
              <el-time-picker v-model="form.arrival_time" placeholder="选择时间" style="width: 100%;" />
            </el-form-item>
            <el-form-item label="备注">
              <el-input type="textarea" v-model="form.note" />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 序列号单独占据右侧一整行 -->
        <el-row>
          <el-col :span="24">
            <el-form-item label="序列号">
              <el-input v-model="form.serial_number" @change="onSerialNumberChange" style="width: 100%;" />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 子项两行 -->
        <el-row :gutter="0" style="margin-bottom: 8px;">
          <el-col :span="8" >
            <el-form-item label="车站" >
              <el-input v-model="form.serial_station_code" readonly style="width: 100%;" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="渠道">
              <el-select v-model="form.serial_channel" placeholder="渠道" style="width: 100%;">
                <el-option label="窗口" value="0" />
                <el-option label="代售点" value="2" />
                <el-option label="自助" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="终端号">
              <el-input v-model="form.serial_terminal" readonly style="width: 100%;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16" style="margin-bottom: 8px;">
          <el-col :span="12">
            <el-form-item label="日期">
              <el-input v-model="form.serial_date" readonly style="width: 100%;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="票号">
              <el-input v-model="form.serial_ticket_no" readonly style="width: 100%;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">提交入库</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
// 引入 Vue 的响应式 API
import { ref, reactive } from 'vue'
// 引入 Element Plus 的消息提示
import { ElMessage } from 'element-plus'

// 乘客列表（假设后端已提供，实际可通过接口获取）
const passengerList = ['张三', '李四', '王五']

// 席别类型列表（假设后端已提供，实际可通过接口获取）
const seatTypeList = [
  { label: '一等座', value: '1' },
  { label: '二等座', value: '2' },
  { label: '商务座', value: '3' },
  { label: '特等座', value: '4' },
  { label: '软卧', value: '5' },
  { label: '硬卧', value: '6' },
  { label: '软座', value: '7' },
  { label: '硬座', value: '8' },
  { label: '无座', value: '9' }
]

// 表单数据，reactive 创建响应式对象
const form = reactive({
  passenger_id: '',
  ticket_id: '',
  train_number: '',
  departure_station_code: '',
  arrival_station_code: '',
  departure_date: '',
  departure_time: '',
  arrival_time: '',
  seat_type: '',
  seat_number: '',
  carriage_number: '',
  ticket_price: '',
  ticket_status: '',
  note: '',
  serial_number: '',         // 完整序列号
  serial_station_code: '',   // 车站代码
  serial_channel: '',        // 渠道
  serial_terminal: '',       // 终端号
  serial_date: '',           // 制票系统日期
  serial_ticket_no: ''       // 票号
})

// 图片相关
const imageUrl = ref<string | null>(null) // 预览用，ref 创建响应式基本类型
const rawFile = ref<File | null>(null)    // 原始文件
const loading = ref(false)                // 识别中状态

// 上传前校验图片格式和大小
function handleBeforeUpload(file: File) {
  const isImage = file.type.startsWith('image/')
  const isLt5M = file.size / 1024 / 1024 < 5
  if (!isImage) {
    ElMessage.error('只能上传图片文件！')
    return false
  }
  if (!isLt5M) {
    ElMessage.error('图片大小不能超过5MB！')
    return false
  }
  // 生成本地预览
  imageUrl.value = URL.createObjectURL(file)
  rawFile.value = file
  return false // 阻止自动上传
}

// 触发上传（调用后端OCR接口）
async function triggerUpload() {
  if (!rawFile.value) {
    ElMessage.warning('请先选择图片')
    return
  }
  loading.value = true
  try {
    // 构造 FormData 发送图片
    const formData = new FormData()
    formData.append('file', rawFile.value)
    // 假设后端OCR接口为 /api/ocr/ticket
    const res = await fetch('/api/ocr/ticket', {
      method: 'POST',
      body: formData
    })
    const data = await res.json()
    if (data.code === 0) {
      // 识别成功，填充表单
      Object.assign(form, data.data)
      ElMessage.success('识别成功，请核对信息')
    } else {
      ElMessage.error(data.msg || '识别失败，请手动填写')
    }
  } catch (e) {
    ElMessage.error('网络错误，识别失败')
  } finally {
    loading.value = false
  }
}

// 序列号变化时自动拆分
function onSerialNumberChange() {
  const sn = form.serial_number || ''
  form.serial_station_code = sn.slice(0, 5)
  form.serial_channel = sn.slice(5, 6)
  form.serial_terminal = sn.slice(6, 10)
  form.serial_date = sn.slice(10, 14)
  form.serial_ticket_no = sn.slice(14, 21)
}

// 提交入库
async function handleSubmit() {
  // 简单校验
  if (!form.passenger_id || !form.train_number || !form.departure_date || !form.departure_time || !form.arrival_time || !form.ticket_status) {
    ElMessage.warning('请填写完整信息')
    return
  }
  try {
    // 假设后端入库接口为 /api/ticket/add
    const res = await fetch('/api/ticket/add', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form)
    })
    const data = await res.json()
    if (data.code === 0) {
      ElMessage.success('入库成功！')
      // 清空表单和图片
      Object.keys(form).forEach(k => (form as any)[k] = '')
      imageUrl.value = null
      rawFile.value = null
    } else {
      ElMessage.error(data.msg || '入库失败')
    }
  } catch (e) {
    ElMessage.error('网络错误，入库失败')
  }
}
</script>

<style scoped>
.ticket-upload-container {
  display: flex;
  gap: 24px;         /* 原来是40px，可以适当减小 */
  padding: 24px 24px 24px 40px; /* 右边多留点空间 */
}
.left-panel, .right-panel {
  flex: 1;
  min-width: 420px;  /* 右侧更宽 */
}
.image-preview {
  margin: 20px 0;
  text-align: center;
}
.image-preview img {
  max-width: 100%;
  max-height: 300px;
  border: 1px solid #eee;
}
</style>
