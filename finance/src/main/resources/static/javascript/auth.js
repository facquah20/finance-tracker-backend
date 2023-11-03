const email = document.getElementById("user-email");
const password = document.getElementById("user-password");

const errorMessageContainer = document.getElementsByClassName("error-message")[0];
const loginButton = document.getElementById('login-button');

const loginApi = "/api/user/login";
const nextPageAfterLogin = '/income';

/** disable login button when clicked to initiate login process */
function disableLoginButton(){
    loginButton.disable = true;
}

/**set disable login button to false when login is unsuccessful */
function setDisableLoginButtonToFalse(){
    loginButton.disable = false;
}

/**
 * @param {Object} params 
 * @returns request body
 */
const getRequestBody = ({email,password})=>{
    const requestBody = {
        method:'POST',
        headers:{
            'Content-Type':'application/json',
        },
        body:JSON.stringify({email,password})

    }
    return requestBody;

}

/** 
 * @param {string} url
 * @description make request to the desired url
 */
 async function makeRequest(url){
    const userEmail = email.value ;
    const userPassword = password.value;
    const requestBody = getRequestBody({email:userEmail,password:userPassword});

    try{
        const response = await fetch(url,requestBody);
        if(response.ok){
            return response.json();
        }
        else{
            return response.status;
        }

    }catch(err){
        console.log('An error occured',err);
    }
}

/** stores the information of the user in local storage */
const storeUserInfo = (userInfo)=>{
    if(typeof userInfo == 'object'){
        localStorage.setItem('userInfo',JSON.stringify(userInfo));
    }

}

/** initiate the login process */
async function initiateLoginProcess(){
    loginButton.innerText = "Loading"
    disableLoginButton();
    const res = await makeRequest(loginApi);
    if(typeof res == 'number'){
        errorMessageContainer.textContent = 'Invalid email or password'
    }
    else{
        console.log(res);
    errorMessageContainer.textContent='Success';
    errorMessageContainer.style.color = 'green';
     storeUserInfo(res);
     window.location.replace(nextPageAfterLogin);

    }    
    setDisableLoginButtonToFalse();
    loginButton.innerText = "Login";
}

loginButton.addEventListener('click',(e)=>{
    e.preventDefault();
    initiateLoginProcess();
})
