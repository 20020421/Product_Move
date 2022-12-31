import classNames from "classnames/bind";
import style from "./WarehousingPage.module.scss";
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { Button, Divider, Form, Input, Select, Space, Tag } from 'antd';
import { useEffect, useState } from "react";
import { get } from "../../utils/request";
import { OverlayTrigger, Tooltip } from "react-bootstrap";
import { warehousingFactory } from "../../utils/requestJson";
import { toast } from "react-hot-toast";
const { Option } = Select;

const cx = classNames.bind(style);



function WarehousingPage() {
    const [warehouses, setWarehouses] = useState([]);
    const [models, setModels] = useState([]);


    useEffect(() => {

        Promise.all([get(`./api/v1/warehouses?page=0&size=100`), get(`/api/v1/models?page=0&size=100`)])
            .then(response => {
                if (response[0].warehouses.length > 0) {
                    const warehousesName = []
                    response[0].warehouses.forEach(warehouse => {
                        warehousesName.push({
                            label: warehouse.name,
                            value: warehouse.name
                        })
                    });
                    setModels(response[1].models)
                    setWarehouses(warehousesName);
                }
            })

    }, [])




    const [form] = Form.useForm();
    const onFinish = (values) => {

        toast.promise(warehousingFactory(values), {
            loading: 'Processing....',
            success: 'Successfully warehousing',
            error: 'Error warehousing',
        }).then(response => {
            form.setFieldValue('warehouseName', "");
            handleChange();
        });
        // warehousingFactory(values)
        // .then(response => {
        //     form.setFieldValue('warehouseName', "");
        // handleChange();
        //     toast.success("Import Successfully !")

        // })
        // .catch(error => toast.error("Import Faily!"))
    };

    const handleChange = () => {
        form.setFieldsValue({
            products: [],
        });
    };
    return (
        <Form form={form} name="dynamic_form_complex" onFinish={onFinish} autoComplete="off" style={{ width: "80%", margin: '0 auto' }}>
            <Form.Item
                name="warehouseName"
                label="Warehouse"
                rules={[
                    {
                        required: true,
                        message: 'Missing area',
                    },
                ]}
            >
                <Select options={warehouses} onChange={handleChange} />
            </Form.Item>
            <Form.List name="products">
                {(fields, { add, remove }) => (
                    <>
                        {fields.map((field, index) => {

                            return (
                                <Space key={field.key} align="baseline">

                                    <FieldProduct field={field} models={models} form={form} />

                                    <MinusCircleOutlined onClick={() => remove(field.name)} />

                                </Space>


                            )
                        })}

                        <Form.Item>
                            <Button type="dashed" onClick={() => add()} block icon={<PlusOutlined />}>
                                Add Products
                            </Button>
                        </Form.Item>
                    </>
                )}
            </Form.List>
            <Form.Item>
                <Button style={{ padding: "20px 30px", display: 'flex', justifyContent: 'center', alignItems: 'center', fontSize: '1.6rem' }} type="primary" htmlType="submit">
                    Import
                </Button>
            </Form.Item>
        </Form>
    );
}

function FieldProduct({ field, models, form }) {

    const [modelChoosed, setModelChoosed] = useState('');
    const [capacities, setCapacities] = useState([]);



    const [colors, setColors] = useState([]);

    function invertHex(hex) {

        return "#" + (Number(`0x1${hex}`) ^ 0xFFFFFF).toString(16).substr(1).toUpperCase()
    }


    const findCodeByColor = (color) => {
        const colorMap = colors.find(colorz => colorz[0] === color);
        return colorMap[1];
    }
    

    useEffect(() => {
        if (modelChoosed !== '') {
            get(`/api/v1/models/${modelChoosed}/colors`)
                .then(response => setColors(Object.entries(response)))
                .catch(error => console.log(error));
        }
        const model = models.find((model) => model.model === modelChoosed);
        if (model) {
            setCapacities(model.capacityList)
        }
        // eslint-disable-next-line
    }, [modelChoosed])

    return (<div style={{ display: 'flex', flexWrap: 'wrap' }}><Form.Item
        noStyle
        shouldUpdate={(prevValues, curValues) =>
            prevValues.area !== curValues.area || prevValues.sights !== curValues.sights
        }
    >
        {() => (
            <Form.Item
                {...field}
                label="Model"
                name={[field.name, 'productModelName']}
                rules={[
                    {
                        required: true,
                        message: 'Missing model',
                    },
                ]}
            >
                <Select
                    disabled={!form.getFieldValue('warehouseName')}
                    style={{
                        width: 130,
                    }}
                    onChange={(value, event) => {
                        setModelChoosed(value)

                    }}
                    value={modelChoosed}
                >
                    {models.map((item, index) => (
                        <Option key={index} value={item.model}>
                            {item.model}
                        </Option>
                    ))}
                </Select>
            </Form.Item>
        )}
    </Form.Item>

        <Form.Item
            noStyle
            shouldUpdate={(prevValues, curValues) =>
                prevValues.area !== curValues.area || prevValues.sights !== curValues.sights
            }
        >
            <Form.Item
                {...field}
                label="Color"
                name={[field.name, 'colorString']}
                rules={[
                    {
                        required: true,
                        message: 'Missing color',
                    },
                ]}
            >
                <Select
                    dropdownStyle={{ '--display': 'flex' }}

                    disabled={!form.getFieldValue('products')[field.name]}
                    style={{
                        width: 130
                    }}


                >
                    {colors.map((color, index) => (
                        <Option key={index} style={{ backgroundColor: `${color[1]}` }} value={color[0]}>
                            <span style={{ backgroundColor: `${color[1]}`, display: 'block', color: `${invertHex(color[1].substr(1))}`, height: '95%', width: '50px', margin: '0 auto', borderRadius: '5px' }}></span>
                        </Option>
                    ))}
                </Select>
            </Form.Item>

        </Form.Item>
        <Form.Item
            noStyle
            shouldUpdate={(prevValues, curValues) =>
                prevValues.area !== curValues.area || prevValues.sights !== curValues.sights
            }
        >
            {
                () => {

                    return (

                        <Form.Item
                            {...field}
                            label="Capacity"
                            name={[field.name, 'capacityInt']}
                            rules={[
                                {
                                    required: true,
                                    message: 'Missing capacity',
                                },
                            ]}
                        >
                            <Select
                                disabled={!form.getFieldValue('products')[field.name]}
                                style={{
                                    width: 130,
                                }}
                            >
                                {capacities.sort((a, b) => a - b).map((capacity, index) => {

                                    const text = capacity >= 1024 ? capacity / 1024 + 'TB' : capacity + "GB"

                                    return (
                                        <Option key={index} value={capacity}>
                                            <span>
                                                {text}
                                            </span>
                                        </Option>
                                    )
                                })}
                            </Select>
                        </Form.Item>
                    )
                }
            }

        </Form.Item>
        <Form.Item
            noStyle
            shouldUpdate={(prevValues, curValues) =>
                prevValues.area !== curValues.area || prevValues.sights !== curValues.sights
            }
        >
            {
                () => {

                    return (

                        <Form.Item
                            {...field}
                            label="Serial"
                            name={[field.name, 'serial']}
                            rules={[
                                {
                                    required: true,
                                    message: 'Missing serial',
                                },
                            ]}
                        >
                            <Input disabled={!form.getFieldValue('products')[field.name]} />
                        </Form.Item>
                    )
                }
            }

        </Form.Item></div>);
}





export default WarehousingPage;