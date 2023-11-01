

/*
* disable login button when initiating a login process
*make the button able in case an error occurs
*when successful alert the success message
 */
 
const form = document.querySelector("form");
const formDate = new FormData(form);
let email = formDate.get("email")
let password =formDate.get("password")



class LoginComponent{
    constructor({email,password}){
        this.email = email,
        this.password = password
this.submitButton = document.querySelector("#login-button");

this.errorMessageHolder = document.querySelector(".error-message");

this.loginUrl = "/api/user/login";

this.submitButton.addEventListener("click",(e)=>{
    e.preventDefault();
    this.initiateLoginProcess();
})
    }

    disabeLoginButton=()=>{
        this.submitButton.disabled = true;     
    }
    ableLoginButton=()=>{
        this.submitButton.disabled = false;
    }
    
    initiateLoginProcess = () =>{
        console.log('password',password)
        const requestBody = {
            email:this.email,
            password:this.password
        }
        const param ={
            method:"POST",
            headers:{
                "Content-Type":"application/json"
            },
            body:JSON.stringify(requestBody)
        }
        console.log(param)
    this.disabeLoginButton();
        fetch(this.loginUrl,param).then(res=>{
            if(res.ok){
                 const data = res.json();
                 return data;
            }
            else{
                this.errorMessageHolder = res.statusText
                return ;
            }
        }).then(data=>{
            console.log(data);
        }).catch(err=>console.log(err)).finally(()=>{
            this.ableLoginButton();
            console.log('button abled again')
        })
    }

}

const loginComponent = new LoginComponent({email:email,password:password});
