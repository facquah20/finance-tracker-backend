document.addEventListener('DOMContentLoaded',()=>{
    const userId = JSON.parse(localStorage.getItem('userInfo'));
    if(userId==null){
        window.location.replace("/");
    }
})

const  humbergerIcon = document.getElementById("humberger-icon");
const closeMenuButton = document.getElementsByClassName("close-icon")[0];

const mobileViewNav = document.getElementsByClassName("mobile-nav-view")[0];

humbergerIcon.addEventListener('click',()=>{
    console.log("opened was clicked");
    const displayType = mobileViewNav.style.display;
    if(displayType=="" || displayType=="none"){
        mobileViewNav.style.display="flex";
    }
})



closeMenuButton.addEventListener('click',()=>{
    console.log("closed was clicked");
    if(mobileViewNav.style.display=="flex"){
        mobileViewNav.style.display="none";
    }
})

document.addEventListener('DOMContentLoaded',()=>{
    const {id,firstname,lastname,email} = JSON.parse(localStorage.getItem('userInfo'));
    document.getElementById('firstname').innerText = firstname;
    document.getElementById('lastname').innerText = lastname;
    document.getElementById('email').innerText = email;
    document.getElementById("userId").innerText = id;
})

document.getElementsByClassName("logout-btn")[0].addEventListener('click',()=>{
    //remove user data
    localStorage.removeItem('userInfo');
    window.location.replace("/");
})
