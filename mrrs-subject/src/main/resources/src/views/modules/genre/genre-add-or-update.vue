<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="类型名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="类型名称"></el-input>
    </el-form-item>
    <el-form-item label="关联影片数" prop="movieCount">
      <el-input v-model="dataForm.movieCount" placeholder="关联影片数"></el-input>
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
          name: '',
          movieCount: '',
          version: '',
          deleted: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          name: [
            { required: true, message: '类型名称不能为空', trigger: 'blur' }
          ],
          movieCount: [
            { required: true, message: '关联影片数不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/subject/genre/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.genre.name
                this.dataForm.movieCount = data.genre.movieCount
                this.dataForm.version = data.genre.version
                this.dataForm.deleted = data.genre.deleted
                this.dataForm.createTime = data.genre.createTime
                this.dataForm.updateTime = data.genre.updateTime
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
              url: this.$http.adornUrl(`/subject/genre/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'movieCount': this.dataForm.movieCount,
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
