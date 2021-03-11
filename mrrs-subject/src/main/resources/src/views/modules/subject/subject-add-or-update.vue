<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="中文名" prop="nameCn">
      <el-input v-model="dataForm.nameCn" placeholder="中文名"></el-input>
    </el-form-item>
    <el-form-item label="英文名" prop="nameUs">
      <el-input v-model="dataForm.nameUs" placeholder="英文名"></el-input>
    </el-form-item>
    <el-form-item label="剧情简介" prop="playDesc">
      <el-input v-model="dataForm.playDesc" placeholder="剧情简介"></el-input>
    </el-form-item>
    <el-form-item label="上映日期" prop="datePublished">
      <el-input v-model="dataForm.datePublished" placeholder="上映日期"></el-input>
    </el-form-item>
    <el-form-item label="影片类型" prop="genre">
      <el-input v-model="dataForm.genre" placeholder="影片类型"></el-input>
    </el-form-item>
    <el-form-item label="影片标签" prop="label">
      <el-input v-model="dataForm.label" placeholder="影片标签"></el-input>
    </el-form-item>
    <el-form-item label="影片评分" prop="rating">
      <el-input v-model="dataForm.rating" placeholder="影片评分"></el-input>
    </el-form-item>
    <el-form-item label="评分人数" prop="playVotes">
      <el-input v-model="dataForm.playVotes" placeholder="评分人数"></el-input>
    </el-form-item>
    <el-form-item label="别民" prop="otherName">
      <el-input v-model="dataForm.otherName" placeholder="别民"></el-input>
    </el-form-item>
    <el-form-item label="演员列表" prop="playActor">
      <el-input v-model="dataForm.playActor" placeholder="演员列表"></el-input>
    </el-form-item>
    <el-form-item label="导演列表" prop="director">
      <el-input v-model="dataForm.director" placeholder="导演列表"></el-input>
    </el-form-item>
    <el-form-item label="编剧列表" prop="playWriter">
      <el-input v-model="dataForm.playWriter" placeholder="编剧列表"></el-input>
    </el-form-item>
    <el-form-item label="制片国家" prop="country">
      <el-input v-model="dataForm.country" placeholder="制片国家"></el-input>
    </el-form-item>
    <el-form-item label="语言" prop="language">
      <el-input v-model="dataForm.language" placeholder="语言"></el-input>
    </el-form-item>
    <el-form-item label="影片海报" prop="playImg">
      <el-input v-model="dataForm.playImg" placeholder="影片海报"></el-input>
    </el-form-item>
    <el-form-item label="影片类型  MOVIE:电影
TVPLAY:电视剧

" prop="subjectType">
      <el-input v-model="dataForm.subjectType" placeholder="影片类型  MOVIE:电影
TVPLAY:电视剧

"></el-input>
    </el-form-item>
    <el-form-item label="电视剧集数" prop="number">
      <el-input v-model="dataForm.number" placeholder="电视剧集数"></el-input>
    </el-form-item>
    <el-form-item label="影片时长" prop="duration">
      <el-input v-model="dataForm.duration" placeholder="影片时长"></el-input>
    </el-form-item>
    <el-form-item label="乐观锁版本" prop="version">
      <el-input v-model="dataForm.version" placeholder="乐观锁版本"></el-input>
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
          nameCn: '',
          nameUs: '',
          playDesc: '',
          datePublished: '',
          genre: '',
          label: '',
          rating: '',
          playVotes: '',
          otherName: '',
          playActor: '',
          director: '',
          playWriter: '',
          country: '',
          language: '',
          playImg: '',
          subjectType: '',
          number: '',
          duration: '',
          version: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          nameCn: [
            { required: true, message: '中文名不能为空', trigger: 'blur' }
          ],
          nameUs: [
            { required: true, message: '英文名不能为空', trigger: 'blur' }
          ],
          playDesc: [
            { required: true, message: '剧情简介不能为空', trigger: 'blur' }
          ],
          datePublished: [
            { required: true, message: '上映日期不能为空', trigger: 'blur' }
          ],
          genre: [
            { required: true, message: '影片类型不能为空', trigger: 'blur' }
          ],
          label: [
            { required: true, message: '影片标签不能为空', trigger: 'blur' }
          ],
          rating: [
            { required: true, message: '影片评分不能为空', trigger: 'blur' }
          ],
          playVotes: [
            { required: true, message: '评分人数不能为空', trigger: 'blur' }
          ],
          otherName: [
            { required: true, message: '别民不能为空', trigger: 'blur' }
          ],
          playActor: [
            { required: true, message: '演员列表不能为空', trigger: 'blur' }
          ],
          director: [
            { required: true, message: '导演列表不能为空', trigger: 'blur' }
          ],
          playWriter: [
            { required: true, message: '编剧列表不能为空', trigger: 'blur' }
          ],
          country: [
            { required: true, message: '制片国家不能为空', trigger: 'blur' }
          ],
          language: [
            { required: true, message: '语言不能为空', trigger: 'blur' }
          ],
          playImg: [
            { required: true, message: '影片海报不能为空', trigger: 'blur' }
          ],
          subjectType: [
            { required: true, message: '影片类型  MOVIE:电影
TVPLAY:电视剧

不能为空', trigger: 'blur' }
          ],
          number: [
            { required: true, message: '电视剧集数不能为空', trigger: 'blur' }
          ],
          duration: [
            { required: true, message: '影片时长不能为空', trigger: 'blur' }
          ],
          version: [
            { required: true, message: '乐观锁版本不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/subject/subject/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.nameCn = data.subject.nameCn
                this.dataForm.nameUs = data.subject.nameUs
                this.dataForm.playDesc = data.subject.playDesc
                this.dataForm.datePublished = data.subject.datePublished
                this.dataForm.genre = data.subject.genre
                this.dataForm.label = data.subject.label
                this.dataForm.rating = data.subject.rating
                this.dataForm.playVotes = data.subject.playVotes
                this.dataForm.otherName = data.subject.otherName
                this.dataForm.playActor = data.subject.playActor
                this.dataForm.director = data.subject.director
                this.dataForm.playWriter = data.subject.playWriter
                this.dataForm.country = data.subject.country
                this.dataForm.language = data.subject.language
                this.dataForm.playImg = data.subject.playImg
                this.dataForm.subjectType = data.subject.subjectType
                this.dataForm.number = data.subject.number
                this.dataForm.duration = data.subject.duration
                this.dataForm.version = data.subject.version
                this.dataForm.createTime = data.subject.createTime
                this.dataForm.updateTime = data.subject.updateTime
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
              url: this.$http.adornUrl(`/subject/subject/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'nameCn': this.dataForm.nameCn,
                'nameUs': this.dataForm.nameUs,
                'playDesc': this.dataForm.playDesc,
                'datePublished': this.dataForm.datePublished,
                'genre': this.dataForm.genre,
                'label': this.dataForm.label,
                'rating': this.dataForm.rating,
                'playVotes': this.dataForm.playVotes,
                'otherName': this.dataForm.otherName,
                'playActor': this.dataForm.playActor,
                'director': this.dataForm.director,
                'playWriter': this.dataForm.playWriter,
                'country': this.dataForm.country,
                'language': this.dataForm.language,
                'playImg': this.dataForm.playImg,
                'subjectType': this.dataForm.subjectType,
                'number': this.dataForm.number,
                'duration': this.dataForm.duration,
                'version': this.dataForm.version,
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
