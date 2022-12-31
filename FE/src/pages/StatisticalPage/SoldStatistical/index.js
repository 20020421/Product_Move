
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

        toast.promise(get(`/api/v1/distributors/statistical/sold?from=${date[0]}&to=${date[1]}`), {
            loading: 'Searching.....',
            success: "Done!",
            error: 'Try again!'
        }).then(response => {
            console.log(response);
            setData(Object.entries(response));
        })

        console.log(date)
    }


    return (<div>

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
            data && <>

                <div className={cx('cards')}>
                    <Card style={{ width: '500px' }} title={`${data[0][0]} sold`} bordered={false}>
                        <div style={{ fontSize: '5rem', fontWeight: '700' }}>
                            {data[0][1]}
                        </div>
                    </Card>
                    <div className={cx('detail')}>
                        {
                            data.map((statistical, index) => {
                                return index !== 0 &&
                                    <Card key={index} style={{ width: '500px' }} title={`${statistical[0]} `} bordered={false}>
                                        <div style={{ fontSize: '5rem', fontWeight: '700' }}>
                                            {statistical[1]}
                                        </div>
                                    </Card> 
                            })
                        }
                    </div>
                </div></>
        }


    </div>);
}

export default SoldStatistical;