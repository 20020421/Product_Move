import { faCheck, faL, faX, faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Form, Input, Select } from "antd";
import classNames from "classnames/bind";
import { useEffect, useState } from "react";
import { toast } from "react-hot-toast";
import { get } from "../../utils/request";
import { addWarrantyAtDistributor } from "../../utils/requestJson";
import style from "./DisWarranty.module.scss";

const cx = classNames.bind(style);

function DisWarranty() {

    const [isUnderWarranty, setIsUnderWarranty] = useState(false)
    const [serial, setSerial] = useState('');
    const [form] = Form.useForm();

    const [show, setShow] = useState(false);

    const [purchaseData, setPurchaseData] = useState();

    const [warehouses, setWarehouses] = useState();

    console.log(purchaseData)

    const onSubmit = async () => {
        try {
            const values = await form.validateFields();
            
            toast.promise(addWarrantyAtDistributor(values), {
                loading: 'Processing ..... ',
                success: 'Done!',
                error: 'Fail, Try again!'
            }).then(response => {
                form.resetFields();
                setShow(false);
                setSerial('')
                setIsUnderWarranty(false);
                setPurchaseData(null);
                setDisableBtn(true)
            })

          } catch (errorInfo) {
            console.log('Failed:', errorInfo);
          }
    }

    console.log(warehouses)
    const onCheckSerial = () => {
        toast.promise(get(`/api/v1/distributors/products/sold/${serial}`), {
            loading: 'Checking......',
            success: 'Done !',
            error: "Dont found with this serial! "
        }).then(response => {
            setIsUnderWarranty(response["Is under Warranty"] === 'true' ? true : false)
            setPurchaseData(response)
            console.log(response["Is under Warranty"])
        })
    }

    const [disableBtn , setDisableBtn] = useState(true);

    return ( <div className={cx('wrapper')}>
        <Input.Group compact>
      <Input
        style={{
          width: 'calc(200px)',
        }}
        placeholder='Input serial'
        onChange={(e) => {
            setSerial(e.target.value)
        }}
      />
      <Button onClick={onCheckSerial} disabled={serial === ''} type="primary">Check</Button>
    </Input.Group>
    {
        purchaseData && 
        <>
    <div className={cx('purchase-data')}>
        <span><strong>Customer name: </strong> {purchaseData["Customer name"]}</span>
        <span><strong>Customer phone: </strong> {purchaseData["Customer phone number"]}</span>
        <span><strong>Model: </strong> {purchaseData["Model"]}</span>
        <span><strong>Buy at: </strong> {purchaseData["Purchase at"]}</span>
        <span><strong>Buy date: </strong> {purchaseData["Purchase date"]}</span>

        <div className={cx('is-under-warranty')}>
            {
                isUnderWarranty ? (<>
                    <FontAwesomeIcon icon={faCheck} /> 
                    <span> Under warranty period</span>
                </>) :(<>
                    <FontAwesomeIcon icon={faXmark} />
                    <span>Out of warranty period OR this product has not been activated</span>
                    </>)
            }
        </div>

        <Button onClick={() => {
            form.setFieldValue('serial', serial)
            get(`/api/v1/warehouses?page=0&size=1000`).then(response => setWarehouses(response.warehouses)) 
            setShow(true)
        }} style={{width: '200px'}} disabled={!isUnderWarranty} type="primary">Create warraty</Button>
    </div>
    {
        show &&
    <Form style={{width: '50%', marginTop: '30px'}}  labelCol={{ span: 6 }} layout="horizontal"  form={form} name="control-hooks" >
      <Form.Item name="serial" label="Serial" disabled={true} rules={[{ required: true }]}>
        <Input disabled={true} />
      </Form.Item>
      <Form.Item name="description" label="Description" initialValue={""} disabled={false} >
        <Input   />
      </Form.Item>

      <Form.Item name="warehouse" label="Warehouse" rule={[{required: true}]}>
      <Select
                dropdownStyle={{
                    zIndex: '999999'
                }}
                showSearch
                placeholder="Select a warehouse"
                optionFilterProp="children"
                onSelect={(value, event) => {

                    if (value !== '') {
                        setDisableBtn(false)
                    }
                }}
                filterOption={(input, option) =>
                    option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                }
            >
                {
                    warehouses &&
                warehouses.map((warehouse, index) => (
                <Select.Option key={index} value={warehouse.name}>{warehouse.name}</Select.Option>
            ))
        }
            </Select>
      </Form.Item>
      <Form.Item>
        <Button style={{float: 'rigth'}} disabled={disableBtn}  type="primary" onClick={onSubmit}>
          Submit
        </Button>
        
      </Form.Item>
    </Form>
    }
    </>
    }






    </div> );
}

export default DisWarranty;