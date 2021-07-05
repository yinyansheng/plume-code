<template>
    <div>
        <el-select v-model="temp" @change="handleSelect" clearable :placeholder="tip">
            <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
            </el-option>
        </el-select>
    </div>
</template>

<script>
    export default {
        name: 'YesNoSelect',
        props: {
            value: {
                type: Number
            },
            tip: {
                type: String
            }
        },
        data() {
            return {
                temp: null,
                options: [
                    {
                        value: 0,
                        label: '无效'
                    }, {
                        value: 1,
                        label: '有效'
                    }]
            }
        },
        watch: {
            value(val) {
                if (val === "") {
                    val = null
                }
                this.$emit('input', val);
                this.temp = val
            }
        },
        methods: {
            handleSelect(val) {
                if (val === "") {
                    val = null
                }
                this.$emit('input', val);
            }
        },
        created() {
            this.temp = 1
        },
    }
</script>
