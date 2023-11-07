/*
* This javascript file handles the signup form
*/

const lastname = document.getElementById("lastname");
const firstname = document.getElementById("firstname");
const email = document.getElementById("user-email");
const password = document.getElementById("password");
const confirmPassword = document.getElementById("confirm-password");

const signupInfoContainer = document.getElementsByClassName("signup-message")[0];

const signupURL = "/api/user/register";
const signupButton = document.getElementById("signup-button");


function disableSignupButton(){
    signupButton.disable = true;
    signupButton.innerText="Loading..."
}
function undoDisable(){
    signupButton.disable =false;
    signupButton.innerText="Signup"
}

/**validates email */
const isValidEmail = (email="")=>{
    const pattern = /^[\w\.-]+@[a-zA-Z\d\.-]+.[a-zA-Z]{2,}$/ ;
    return pattern.test(email);
}

/**perform validation of the fields before sending request to server */
const validateFields = ({lastname="",firstname="",password="",email})=>{
    const minimumPasswordLength = 8;
    if(lastname.trim().length==0 || firstname.trim().length==0){
        return "lastname or firstname cannot be blank";
    }
    else if(password.length<minimumPasswordLength){
        return "password must be at least 8 characters long"
    }
    else return isValidEmail(email);
}

/**constructs the body of the signup request */
const getSignupBody = ({firstname,lastname,email,password})=>{
    const requestBody = {
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify({firstname,lastname,email,password})
    }
    return requestBody;

}

async function makeRequest(requestBody,url){
    try{
        const res = await fetch(url,requestBody);
        if(res.ok){
            return res.text();
        }
        else{
            return res.text();
        }
    }catch(err){
        console.log(err);
    }
}
function sendSignupRequestToServer(url){
    /*check if password equals confirm-password */
    if(password.value === confirmPassword.value){
        /*perform validation */
    const isValidField = validateFields({lastname:lastname.value,firstname:firstname.value,email:email.value,password:password.value})

    if(typeof isValidField == 'string'){
        signupInfoContainer.textContent = isValidField;
        return ;
    }
    else if(typeof isValidField == 'boolean' && isValidField==true){
        /* now you can make request */
        console.log("now sending request")
        signupButton.innerText='Signing up...';
        const params = getSignupBody({firstname:firstname.value,lastname:lastname.value,email:email.value,password:password.value,})
        makeRequest(params,url).then(
            res=>{
                signupInfoContainer.textContent=res;
            }
        ).catch(err=>console.log(err)).finally(()=>{
            signupButton.innerText='Signup'
        })
    }
    else {
        signupInfoContainer.textContent = "Invalid email address";
        return;
    }

    }else{
        signupInfoContainer.textContent="password does not match";
    }
}



 signupButton.addEventListener('click',(e)=>{
    console.log("button was clicked");
    e.preventDefault();
    sendSignupRequestToServer(signupURL);

 })