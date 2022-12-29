import "./Form.module.scss"
import { Button, Form, Input, Select } from "antd";
import classNames from "classnames/bind";
import style from "./Purchase.module.scss";
import { toast } from "react-hot-toast";
import { newPurchase } from "../../utils/requestJson";
import { get } from "../../utils/request";
import { useEffect, useState } from "react";

const cx = classNames.bind(style);

function Purchase() {

    const [form] = Form.useForm();
    const onChange = (value) => {
        console.log(`selected ${value}`);
      };
      const onSearch = (value) => {
        console.log('search:', value);
      };

    const getAllSerial = () => {
        get('/api/v1/distributors/serials')
        .then(response => {
            console.log(response);
            setSerials(response)
        })
        .catch(error => console.log(error));
        
    }

    const [serials, setSerials] = useState([]);
    console.log(serials)

    useEffect(() => {
        getAllSerial()
    } ,[])

    const handleSubmit = async () => {
        try {
            const values = await form.validateFields();
            
            
            toast.promise(get(`/api/v1/distributors/products/${values.serial}`), {
                loading: "Checking serial....",
                error: 'Cant found product in warehouse!'
            }).then(() => {
                toast.promise(newPurchase(values), {
                    loading: "Processing....",
                    success: "Successfully!",
                    error: 'Fail to purchase. Check again!'
                }).then(response => {
                    form.resetFields();
                })
            })
          } catch (errorInfo) {
            console.log('Failed:', errorInfo);
          }
    }
    return ( <div className={cx('wrapper')}>
        <h1>New Purchase</h1>
        <Form form={form} layout="vertical" autoComplete="off">
        <Form.Item name="customerName" label="Customer name" rules={[{ required: true, message: 'Please input customer name!' }]}>
          <Input />
        </Form.Item>
        <Form.Item name="customerPhone" label="Customer phone" rules={[{ required: true, message: 'Please input customer phone!' }]} >
          <Input />
        </Form.Item>
        <Form.Item name="serial" label="Product Serial" rules={[{ required: true, message: 'Please input serial!' }]}>
        <Select
                dropdownStyle={{
                    zIndex: '999999'
                }}
                showSearch
                placeholder="Select a serial"
                optionFilterProp="children"
                onSelect={(value, event) => {
                    onChange(value);
                }}
                filterOption={(input, option) =>
                    option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                }
            >
                {
                    serials.map((serial, index) => (
                        <Select.Option key={index} value={serial}>{serial}</Select.Option>
                    ))
                }
            </Select>
        </Form.Item>
        <Button type="primary" onClick={handleSubmit}>Submit</Button>
      </Form>
    </div> );
}

export default Purchase;