<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="topic名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="datasourceTopic">
              <a-input v-model="model.datasourceTopic" placeholder="请输入topic名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="字段名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fieldName">
              <a-input v-model="model.fieldName" placeholder="请输入字段名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="判断表达式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="expression">
              <a-input v-model="model.expression" placeholder="请输入判断表达式"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="写入表名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tableName">
              <a-input v-model="model.tableName" placeholder="请输入写入表名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="业务名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="buss">
              <a-input v-model="model.buss" placeholder="请输入业务名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="创建者" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="createName">
              <a-input v-model="model.createName" placeholder="请输入创建者"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'CgOrmRuleForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
           datasourceTopic: [
              { required: true, message: '请输入topic名称!'},
           ],
           fieldName: [
              { required: true, message: '请输入字段名!'},
           ],
           expression: [
              { required: true, message: '请输入判断表达式!'},
           ],
           tableName: [
              { required: true, message: '请输入写入表名!'},
           ],
           buss: [
              { required: true, message: '请输入业务名!'},
           ],
           createName: [
              { required: true, message: '请输入创建者!'},
           ],
        },
        url: {
          add: "/cg_orm_rule/cgOrmRule/add",
          edit: "/cg_orm_rule/cgOrmRule/edit",
          queryById: "/cg_orm_rule/cgOrmRule/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>