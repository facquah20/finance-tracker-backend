const  humbergerIcon = document.getElementById("humberger-icon");
const closeMenuButton = document.getElementsByClassName("close-icon")[0];

const mobileViewNav = document.getElementsByClassName("mobile-nav-view")[0];

const addIncomeBtn = document.getElementsByClassName("add-income")[0];
const refreshForUpdateBtn = document.getElementsByClassName("refresh-for-update")[0];
const modal = document.getElementById("modal");
const closeModalBtn = document.getElementById("closeModalBtn");
const backendMessageHolderForModal = document.getElementById("backend-message");
//when clicked sends a post request to server to add new income
const addBtn = document.getElementById("add-btn");

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

/*
*Write a function that handles update
*write a function that handles delete 
*write a function that calculates total income
*write a function that allows for a new income to be added

*/

addIncomeBtn.addEventListener("click",()=>{
    modal.style.display = "block";

})

closeModalBtn.addEventListener("click",()=>{
    modal.style.display = "none";
})

/**
 * 
 * @param {number} incomeId 
 * @param {number} amount 
 * @returns url
 */
function getUpdateAmountRequestUrl(incomeId,amount){
    return `/api/user/income/update-amount?incomeId=${incomeId}&&amount=${amount}`;
}

/**
 * @param {string} incomeId
 * @returns url
 */
function getDeleteRequestUrl(incomeId){
    return `/api/user/income?incomeId=${incomeId}`; 
}

/**
 * 
 * @param {string} url 
 * @description make a request to the server to delete a given income
 */
function sendDeleteRequestToServer(url){
    fetch(url,{method:"DELETE"}).then(res=>{
        if(res.ok){
            return "Deleted";
        }
        else{
            return "Delete unsuccessful";
        }
    }).then(data=>{
        alert(data);
    }).catch(e=>console.log(err));

}


/**
 * 
 * @param {string} url 
 * @description sends a request to update income amount
 */
function sendUpdateRequestToServer(url){
    fetch(url,{method:"PUT"}).then(res=>{
        if(res.ok){
            return res.text();
        }
        else{
            return res.text()
        }
    }).then(data=>{
        alert(data);
    }).catch(err=>console.log(err));
}

/**
 * 
 * @param {number} incomeId 
 */
function createDeleteModal(incomeId){
    let modal = document.createElement('div');
    modal.className = "modal";

    let modalContent = document.createElement('div');
    modalContent.className = "modal-content";
    
    let closeButton = document.createElement("span");
    closeButton.className = "close";
    closeButton.innerHTML = "&times;";

    let modalText = document.createElement('p');
    modalText.innerHTML = "Do you want to Delete this item?"

    let deleteButton = document.createElement('button');
    deleteButton.className = 'delete-item';
    deleteButton.innerText = 'Yes';

    deleteButton.addEventListener('click',()=>{
        const url = getDeleteRequestUrl(incomeId);
        sendDeleteRequestToServer(url);

    })

    //append elements to the modal
    modalContent.appendChild(closeButton);
    modalContent.appendChild(modalText);
    modalContent.appendChild(deleteButton);
    modal.appendChild(modalContent);
    document.body.appendChild(modal);

    modal.style.display = "block";

    closeButton.addEventListener("click",()=>{
        modal.style.display = "none";
        document.body.removeChild(modal);
    })

}


/**
 * 
 * @param {number} incomeId 
 * @description generates a modal that allows a user to update amount
 */
function generateModalToUpdateAmount(incomeId){
    let modal = document.createElement('div');
    modal.className = "modal";

    let modalContent = document.createElement('div');
    modalContent.className = "modal-content";
    
    let closeButton = document.createElement("span");
    closeButton.className = "close";
    closeButton.innerHTML = "&times;";

    closeButton.addEventListener("click",()=>{
        modal.style.display = "none";
        document.body.removeChild(modal);
    })

    let modalText = document.createElement('p');
    modalText.innerHTML = "Enter new Amount"

    let inputArea = document.createElement('input')
    inputArea.type="number";
    inputArea.step = "0.01";
    inputArea.className="update-amount-input-area";

    let updateButton = document.createElement('button');
    updateButton.className = 'update-button';
    updateButton.innerText='Update';

    modalContent.appendChild(closeButton);
    modalContent.appendChild(modalText);
    modalContent.appendChild(inputArea);
    modalContent.appendChild(updateButton);
    modal.appendChild(modalContent);
    document.body.appendChild(modal);
    modal.style.display = "block";

    updateButton.addEventListener('click',()=>{
        let amount = inputArea.value;
        const url = getUpdateAmountRequestUrl(incomeId,parseFloat(amount));
        sendUpdateRequestToServer(url);
    })


}

const getParamUrlToAddIncome = ()=>{
    const userId = JSON.parse(localStorage.getItem('userInfo')).id;
    const source = document.getElementById("source-input-area").value;
    const amount = document.getElementById("amount-input-area").value;
   
    return `/api/user/income?userId=${userId}&&source=${source}&&amount=${parseFloat(amount)}`;

}

 async function addNewIncome(url){
    const param = {
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        }
    }
    const res = await fetch(url,param);
    return res;
}


addBtn.addEventListener("click",()=>{
    const url = getParamUrlToAddIncome();
    addBtn.setAttribute("disable",true);
    addBtn.innerText="Adding..."
    addNewIncome(url).then(res=>{
        if(res.ok){
            const result = res.text();
            return result;
        }
        else{
            const result = res.text();
            return result;
        }
        
    }).then(data =>{
        backendMessageHolderForModal.textContent = data;

    }).catch(err=>console.log(err)).finally(
        ()=>{
            addBtn.setAttribute("disable",false);
            addBtn.innerText ="Add";
            document.getElementById("source-input-area").value="";
            document.getElementById("amount-input-area").value="";
        }
    )
})

//clears table and replace it with the new data

function clearTable(){
    const table = document.getElementById("table-data");
    while(table.rows.length>1){
        table.deleteRow(1); //delete all rows except the header row
    }
}

//Fetch data from server to get all incomes

const tableDataTemplate=(id,amount,source)=>{
    const row = document.createElement("tr");
    const idTd = document.createElement('td');
    const sourceTd = document.createElement('td');
    const amountTd = document.createElement('td');

    const img1 = document.createElement('img');
    const img2 = document.createElement('img');
    const imagesTd = document.createElement('td');

    img1.addEventListener('click', ()=>{
        //calls to create a delete modal alert
        createDeleteModal(id);
    })

    img2.addEventListener('click',()=>{
        generateModalToUpdateAmount(id);
    })

    //setting the values for each
    idTd.textContent = id;
    sourceTd.textContent =source;
    amountTd.textContent =amount;
    img1.src="/images/delete-icon.jpg"
    img1.width=40;
    img1.height=40;
    img2.src="/images/edit-icon.png"
    img2.width=40;
    img2.height=40;

    imagesTd.append(img1,img2);
    row.append(idTd,amountTd,sourceTd,imagesTd);
    return row;
}

//fetching  a list of incomes from server
const getUrlForFindingIncome = ()=>{
    const userId = JSON.parse(localStorage.getItem("userInfo")).id;
    return `/api/user/income?userId=${userId}`;
}

async function getAllUserIncome(){
    const url = getUrlForFindingIncome();
    const res = await fetch(url)
    return res;
}

const tabulateData = ()=>{
    const table = document.getElementById("table-data");
    getAllUserIncome().then(res=>{
        if(res.ok){
            const data = res.json();
            return data;
        }
    }).then(data=>{
        if(data.length>0){
            let totalIncome = 0;
            data.map(item=>{
                const {id,amount,source} = item;
                totalIncome+= parseFloat(amount);
                table.appendChild(tableDataTemplate(id,amount,source)) 

            })
            document.getElementById('total-income').innerText =totalIncome.toFixed(2);
        }
    }
    ).catch(err=>console.log(err)).finally(
        ()=>console.log('request was made')
    )
}

document.addEventListener("DOMContentLoaded",()=>{
    tabulateData();
})


refreshForUpdateBtn.addEventListener('click',()=>{
    clearTable();
    tabulateData();
})

