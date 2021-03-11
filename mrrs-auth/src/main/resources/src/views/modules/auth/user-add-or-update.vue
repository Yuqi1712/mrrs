<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户账号" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="用户账号"></el-input>
    </el-form-item>
    <el-form-item label="用户密码" prop="userPassword">
      <el-input v-model="dataForm.userPassword" placeholder="用户密码"></el-input>
    </el-form-item>
    <el-form-item label="用户名称" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="用户名称"></el-input>
    </el-form-item>
    <el-form-item label="用户年龄" prop="userAge">
      <el-input v-model="dataForm.userAge" placeholder="用户年龄"></el-input>
    </el-form-item>
    <el-form-item label="MAN-男，WOMAN-女" prop="userGender">
      <el-input v-model="dataForm.userGender" placeholder="MAN-男，WOMAN-女"></el-input>
    </el-form-item>
    <el-form-item label="用户头像url" prop="userIcon">
      <el-input v-model="dataForm.userIcon" placeholder="用户头像url"></el-input>
    </el-form-item>
    <el-form-item label="用户邮箱" prop="userEmail">
      <el-input v-model="dataForm.userEmail" placeholder="用户邮箱"></el-input>
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
          userAccount: '',
          userPassword: '',
          userName: '',
          userAge: '',
          userGender: '',
          userIcon: '',
          userEmail: '',
          version: '',
          deleted: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          userAccount: [
            { required: true, message: '用户账号不能为空', trigger: 'blur' }
          ],
          userPassword: [
            { required: true, message: '用户密码不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '用户名称不能为空', trigger: 'blur' }
          ],
          userAge: [
            { required: true, message: '用户年龄不能为空', trigger: 'blur' }
          ],
          userGender: [
            { required: true, message: 'MAN-男，WOMAN-女不能为空', trigger: 'blur' }
          ],
          userIcon: [
            { required: true, message: '用户头像url不能为空', trigger: 'blur' }
          ],
          userEmail: [
            { required: true, message: '用户邮箱不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/auth/user/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userAccount = data.user.userAccount
                this.dataForm.userPassword = data.user.userPassword
                this.dataForm.userName = data.user.userName
                this.dataForm.userAge = data.user.userAge
                this.dataForm.userGender = data.user.userGender
                this.dataForm.userIcon = data.user.userIcon
                this.dataForm.userEmail = data.user.userEmail
                this.dataForm.version = data.user.version
                this.dataForm.deleted = data.user.deleted
                this.dataForm.createTime = data.user.createTime
                this.dataForm.updateTime = data.user.updateTime
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
              url: this.$http.adornUrl(`/auth/user/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userAccount': this.dataForm.userAccount,
                'userPassword': this.dataForm.userPassword,
                'userName': this.dataForm.userName,
                'userAge': this.dataForm.userAge,
                'userGender': this.dataForm.userGender,
                'userIcon': this.dataForm.userIcon,
                'userEmail': this.dataForm.userEmail,
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
