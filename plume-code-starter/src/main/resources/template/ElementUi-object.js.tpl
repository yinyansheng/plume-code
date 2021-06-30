export let searchForm = {
#foreach(${fieldModel} in ${fieldModelList})
    ${fieldModel.name},
#end
}

export class ${ClassName}Ent {
#foreach(${fieldModel} in ${fieldModelList})
    ${fieldModel.name}
#end
}

export let formRules = {
#foreach(${fieldModel} in ${fieldModelList})
    ${fieldModel.name}: [{required: true, message: '请填写${fieldModel.comment}', trigger: 'change'}],
#end
}
