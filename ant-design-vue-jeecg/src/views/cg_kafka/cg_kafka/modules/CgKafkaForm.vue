<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="脚本名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="shellName">
              <a-input v-model="model.shellName" placeholder="请输入脚本名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="kafka集群" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="kafkaClusterName">
              <j-popup
                v-model="model.kafkaClusterName"
                field="kafkaClusterName"
                org-fields="kafka_cluster_name,broker_services"
                dest-fields="kafkaClusterName,kafkaBrokerService"
                code="cg_kc"
                @input="popupCallback"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="kafka连接信息" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="kafkaBrokerService">
              <a-input v-model="model.kafkaBrokerService" placeholder="请输入kafka连接信息" disabled ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="topic名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="topicName">
              <a-input v-model="model.topicName" placeholder="请输入topic名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="分区数" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="topicPartition">
              <a-input-number v-model="model.topicPartition" placeholder="请输入分区数" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="内存大小" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="useMem">
              <j-dict-select-tag type="list" v-model="model.useMem" dictCode="use_mem" placeholder="请选择内存大小" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="运行主机" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="kafkaNode">
              <j-multi-select-tag type="list_multi" v-model="model.kafkaNode" dictCode="kafka_node" placeholder="请选择运行主机" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="业务负责人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="userBy">
              <a-input v-model="model.userBy" placeholder="请输入业务负责人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="业务用途" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="userFor">
              <a-input v-model="model.userFor" placeholder="请输入业务用途"  ></a-input>
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
    name: 'CgKafkaForm',
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
          add: "/cg_kafka/cgKafka/add",
          edit: "/cg_kafka/cgKafka/edit",
          queryById: "/cg_kafka/cgKafka/queryById"
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
      popupCallback(value,row){
         this.model = Object.assign(this.model, row);
      },
    }
  }
</script>