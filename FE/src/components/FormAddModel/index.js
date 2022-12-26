import { Button, Divider, Input, Select, Space, Tag } from "antd";
import classNames from "classnames/bind";
import { useEffect, useRef, useState } from "react";
import { get } from "../../utils/request";
import style from "./FormAddModel.module.scss";
import "./CustomSelectStyle.scss";
import { Form, OverlayTrigger, Tooltip } from "react-bootstrap";
import ColorPicker from "rc-color-picker";
import { addColor } from "../../utils/requestJson";


const cx = classNames.bind(style);

function FormAddModel({ state, setState }) {


    const [options, setOptions] = useState({
        colors: [],
        capacities: [],
    })




    const [newColor, setNewColor] = useState({
        color: '',
        code: ''
    })

    const inputRef = useRef(null);

    const setColorChange = (event) => {
        setNewColor({
            ...newColor,
            [event.target.name]: event.target.value
        })
    }

    const handleAddNewcolor = () => {
        if (newColor.code.startsWith('#') && newColor.code.length <= 7) {
            const data = {
                colors: {
                    [newColor.color]: newColor.code
                }
            }

            addColor(data)
                .then(response => {
                    get("/api/v1/models/colors")
                        .then(response => {
                            setOptions({
                                ...options,
                                colors: Object.entries(response),
                            })

                            setNewColor({
                                color: '',
                                code: ''
                            })
                        })
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }







    useEffect(() => {
        Promise.all([get("/api/v1/models/colors"), get("/api/v1/models/capacities")]).then((response) => {
            setOptions({
                ...options,
                colors: Object.entries(response[0]),
                capacities: response[1]
            })
        })

    }, [])

    const handleOnChange = (e) => {
        setState({
            ...state,
            [e.target.id]: e.target.value
        })
    }

    const ColorByCode = (codes) => {
        const colors = []
        codes.forEach(code => {
            const color = options.colors.find(colorDB => colorDB[1] === code);
            colors.push(color[0]);
        });

        return colors;
    }


    function invertHex(hex) {
        
        return "#" + (Number(`0x1${hex}`) ^ 0xFFFFFF).toString(16).substr(1).toUpperCase()
      }
    const tagRender = (props) => {
        const { label, value, closable, onClose } = props;

        const onPreventMouseDown = (event) => {
            event.preventDefault();
            event.stopPropagation();
        };

        const colorText = invertHex(value.substr(1));
        return (
            <OverlayTrigger

                placement='top'
                overlay={
                    <Tooltip >
                        {label}
                    </Tooltip>
                }
            >
                <Tag
                    color={value}
                    onMouseDown={onPreventMouseDown}
                    closable={closable}
                    onClose={onClose}
                    style={{ marginRight: 3, '--color': colorText }}
                >

                </Tag>
            </OverlayTrigger>
        );
    };

    return (<form className={cx('form')}>
        <div className={cx('form-group')}>
            <label htmlFor="model" className={cx('form-label')}>
                Model
            </label>
            <input autoComplete="off" spellCheck="off" value={state.model} onChange={handleOnChange} type='text' name="model" className={cx('form-control', 'field')} id="model" />
        </div>
        <div className={cx('form-group')}>
            <label htmlFor="chip" className={cx('form-label')}>
                Chip
            </label>
            <input autoComplete="off" spellCheck="off" value={state.chip} onChange={handleOnChange} type='text' name="chip" className={cx('form-control', 'field')} id="chip" />
        </div>
        <div className={cx('form-group')}>
            <label htmlFor="branch" className={cx('form-label')}>Color Set</label>
            <div className={cx('form-control', 'field', 'select')}>
                <Select
                    dropdownStyle={{
                        zIndex: '999999',
                        "--display": 'grid'
                    }}
                    size="large"

                    tagRender={tagRender}
                    // showSearch
                    // style={{ width: 200 }}
                    placeholder="Select color"
                    optionFilterProp="children"
                    mode="multiple"
                    className="colors"
                    onChange={(value, event) => {
                        setState({
                            ...state,
                            colorString: ColorByCode(value)
                        })

                    }}
                    dropdownRender={(menu) => (
                        <>
                            {menu}
                            <Divider
                                style={{
                                    margin: '8px 0',
                                }}
                            />
                            <Space
                                style={{
                                    padding: '0 8px 4px',
                                }}
                            >
                                <Input
                                    name="color"
                                    spellCheck="off"
                                    placeholder="Color name"
                                    value={newColor.color}
                                    onChange={setColorChange}
                                    ref={inputRef}
                                />
                                <Input
                                    name="code"
                                    spellCheck="off"
                                    placeholder="Color hex"
                                    value={newColor.code}
                                    onChange={setColorChange}
                                    ref={inputRef}
                                />

                                <Button type="text" onClick={handleAddNewcolor}>
                                    Add color
                                </Button>
                            </Space>
                        </>
                    )}

                    filterOption={(input, option) =>
                        option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                    }
                >
                    {
                        options.colors.map((color, index) => {

                            const colorText = invertHex(color[1].substr(1))

                            return (
                                

                                    <Select.Option code={color[1]} style={{ backgroundColor: `${color[1]}`, '--color': colorText }} key={index} value={color[1]}>
                                        {color[0]}
                                    </Select.Option>

                                )
                        }
                        )
                    }
                </Select>

            </div>
        </div>
        <div className={cx('form-group')}>
            <label htmlFor="branch" className={cx('form-label')}>Capacity Set</label>
            <div className={cx('form-control', 'field', 'select')}>
                <Select

                    size="large"
                    dropdownStyle={{
                        zIndex: '999999'
                    }}
                    showSearch
                    style={{ width: 200 }}
                    placeholder="Select capacity"
                    optionFilterProp="children"
                    mode="multiple"
                    className="capacities"
                    onChange={(value, event) => {
                        setState({
                            ...state,
                            capacityList: value
                        })

                    }}
                    filterOption={(input, option) =>
                        option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                    }
                >
                    {
                        options.capacities.map((capacity, index) => {
                            const text = capacity >= 1024 ? `${capacity / 1024}TB` : `${capacity}GB`

                            return (
                                <Select.Option key={index} value={capacity}>
                                    {text}
                                </Select.Option>)
                        })
                    }
                </Select>

            </div>
        </div>

    </form>);
}

export default FormAddModel;