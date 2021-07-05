export let searchForm = {
    id : null,
    name : null,
    password : null,
    version : null,
}

export class SmartUserEnt {
    id
    name
    password
    version
}

export let formRules = {
    id: [{required: true, message: '请填写', trigger: 'change'}],
    name: [{required: true, message: '请填写', trigger: 'change'}],
    password: [{required: true, message: '请填写', trigger: 'change'}],
    version: [{required: true, message: '请填写version', trigger: 'change'}],
}
