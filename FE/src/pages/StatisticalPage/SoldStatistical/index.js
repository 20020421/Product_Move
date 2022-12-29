
import { Button, Card, DatePicker, Space } from 'antd';

import classNames from 'classnames/bind';
import { useEffect, useState } from 'react';
import { toast } from 'react-hot-toast';
import { get } from '../../../utils/request';
import style from "../Statistical.module.scss";

const cx = classNames.bind(style)

const dateFormat = 'DD-MM-YYYY';

function SoldStatistical() {

    const [date, setDate] = useState()
    const [data, setData] = useState(null);
    const onChange = (value, dateString) => {
        setDate(dateString)
        if (dateString[0] === '' || dateString[1] === '') {
            setData(null)
        }
    }


    const onOk = (value) => {
        console.log("onOk: ", value)
    }

    const handleSearch = () => {

        toast.promise(get(`/api/v1/distributors/productsSold?from=${date[0]}&to=${date[1]}`), {
            loading: 'Searching.....',
            success: "Done!",
            error: 'Try again!'
        }).then(response => {
            console.log(response);
            setData(response);
        })

        console.log(date)
    }


    return ( <div>

            <h1>Statistics of products sold</h1>

            <div className={cx('options')}>
                <DatePicker.RangePicker 
                allowClear={true}
                placeholder={['From', 'To']} 
                onChange={onChange}
                onOk={onOk}
                
                />
                <Button disabled={!date || date[0] === '' || date[1] === ''} onClick={handleSearch} type='primary'>Search</Button>
            </div>
            {
                data && <Card style={{width: '500px'}} title={`Number of items sold (${date[0]}  -> ${date[1]}) `} bordered={false}>
                <div style={{fontSize: '5rem', fontWeight: '700'}}>
                    {data.length}
                </div>
              </Card>
            }


    </div> );
}

export default SoldStatistical;