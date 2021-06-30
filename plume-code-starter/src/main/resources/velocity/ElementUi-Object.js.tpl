export let searchForm = {
  configKey: null,
  configType: null,
  configDesc: null,
}

export class ${ClassName}ENT {

}

export let formRules = {
  //type:'number'
  configType: [{ required: true, message: '请填写分类', trigger: 'blur' }],
  configKey: [{ required: true, message: '请填写描述', trigger: 'blur' }],
  configValue: [{ required: true, message: '请填写值', trigger: 'blur' }],
  isValid: [{ required: true, message: '请填写是否有效 0:无效 1:有效', trigger: 'blur', type: 'number' }],
}
