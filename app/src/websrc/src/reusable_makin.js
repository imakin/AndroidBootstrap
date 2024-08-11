/**
 * @author Muhammad Ramdan Izzulmakin
 * @date 2024-August
 * @license MIT 
 * copyright (c) 2024 Muhammad Izzulmakin
 */
//reusable functions
const el = function(selector) {
    return document.querySelector(selector);
}
const els = function(selector) {
    const elements = Array.from(document.querySelectorAll(selector));

    return new Proxy({}, {
        get(target, prop) {
            if (typeof prop === 'string' && prop in elements) {
                return elements[prop];
            }

            return (...args) => {
                elements.forEach(element => {
                    if (typeof element[prop] === 'function') {
                        element[prop](...args);
                    }
                });
            };
        }
    });
}
/**
 * Debounces a function, ensuring that it is only called after a specified timeout period has elapsed since the last time it was invoked.
 *
 * @param {function} func - The function to be debounced.
 * @param {number} [timeout=1000] - The timeout period in milliseconds.
 * @returns {function} - A new function that, when called, will only execute the original function after the specified timeout period has elapsed.
 * 
 * example 
        document.querySelector('#myinput').addEventListener(
            'input',
            debounce(e=>{
                let el = e.target;
                console.log(el.value);
            })
        )
 */
const debounce = function(func, timeout = 1000){
    let name = `debouncer_timer_${func.name}`;
    window[name] = null;
    return (...args) => {
        clearTimeout(window[name]);
        window[name] = setTimeout(() => { func.apply(this, args); }, timeout);
    };
}
//end reusable

export {
    el,
    els,
    debounce
}