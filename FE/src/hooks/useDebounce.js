import { useEffect, useState } from 'react';

function useDebounce(value, delay) {
    const [debounceValue, setDebounceValue] = useState(value);

    useEffect(() => {
        const debounceTimeOut = setTimeout(() => {
            setDebounceValue(value);
        }, delay);

        return () => clearTimeout(debounceTimeOut);
        // eslint-disable-next-line
    }, [value]);

    return debounceValue;
}

export default useDebounce;
