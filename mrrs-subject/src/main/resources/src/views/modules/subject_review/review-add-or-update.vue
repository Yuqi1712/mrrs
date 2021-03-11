<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="影片id" prop="subject">
      <el-input v-model="dataForm.subject" placeholder="影片id"></el-input>
    </el-form-item>
    <el-form-item label="评论者名称" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="评论者名称"></el-input>
    </el-form-item>
    <el-form-item label="评论内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="评论内容"></el-input>
    </el-form-item>
    <el-form-item label="评论者头像" prop="userIcon">
      <el-input v-model="dataForm.userIcon" placeholder="评论者头像"></el-input>
    </el-form-item>
    <el-form-item label="评级  1,2,3,4,5 对应星级" prop="rating">
      <el-input v-model="dataForm.rating" placeholder="评级  1,2,3,4,5 对应星级"></el-input>
    </el-form-item>
    <el-form-item label="评论日期" prop="reviewDate">
      <el-input v-model="dataForm.reviewDate" placeholder="评论日期"></el-input>
    </el-form-item>
    <el-form-item label="乐观锁版本" prop="version">
      <el-input v-model="dataForm.version" placeholder="乐观锁版本"></el-input>
    </el-form-item>
    <el-form-item label="是否被删除: 0-未删除,1-删除" prop="deleted">
      <el-input v-model="dataForm.deleted" placeholder="是否被删除: 0-未删除,1-删除"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="修改时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          subject: '',
          userName: '',
          content: '',
          userIcon: '',
          rating: '',
          reviewDate: '',
          version: '',
          deleted: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          subject: [
            { required: true, message: '影片id不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '评论者名称不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '评论内容不能为空', trigger: 'blur' }
          ],
          userIcon: [
            { required: true, message: '评论者头像不能为空', trigger: 'blur' }
          ],
          rating: [
            { required: true, message: '评级  1,2,3,4,5 对应星级不能为空', trigger: 'blur' }
          ],
          reviewDate: [
            { required: true, message: '评论日期不能为空', trigger: 'blur' }
          ],
          version: [
            { required: true, message: '乐观锁版本不能为空', trigger: 'blur' }
          ],
          deleted: [
            { required: true, message: '是否被删除: 0-未删除,1-删除不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/subject/review/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.subject = data.review.subject
                this.dataForm.userName = data.review.userName
                this.dataForm.content = data.review.content
                this.dataForm.userIcon = data.review.userIcon
                this.dataForm.rating = data.review.rating
                this.dataForm.reviewDate = data.review.reviewDate
                this.dataForm.version = data.review.version
                this.dataForm.deleted = data.review.deleted
                this.dataForm.createTime = data.review.createTime
                this.dataForm.updateTime = data.review.updateTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/subject/review/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'subject': this.dataForm.subject,
                'userName': this.dataForm.userName,
                'content': this.dataForm.content,
                'userIcon': this.dataForm.userIcon,
                'rating': this.dataForm.rating,
                'reviewDate': this.dataForm.reviewDate,
                'version': this.dataForm.version,
                'deleted': this.dataForm.deleted,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
