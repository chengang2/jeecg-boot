<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="流程名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="workflowName">
              <a-input v-model="model.workflowName" placeholder="请输入流程名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="主机名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="endpoint">
              <j-multi-select-tag type="list_multi" v-model="model.endpoint" dictCode="cg_n9e_node,endpoint,endpoint" placeholder="请选择主机名" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="告警指标" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="metric">
              <a-input v-model="model.metric" placeholder="请输入告警指标"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="告警标签" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tags">
              <a-input v-model="model.tags" placeholder="请输入告警标签"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="回调脚本" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="runShell">
              <a-input v-model="model.runShell" placeholder="请输入回调脚本"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="执行回调用户" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="runUser">
              <a-input v-model="model.runUser" placeholder="请输入执行回调用户"  ></a-input>
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
    name: 'CgN9eCallbackForm',
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
        },
        url: {
          add: "/cg_n9e_callback/cgN9eCallback/add",
          edit: "/cg_n9e_callback/cgN9eCallback/edit",
          queryById: "/cg_n9e_callback/cgN9eCallback/queryById"
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