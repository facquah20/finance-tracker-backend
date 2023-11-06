
document.addEventListener('DOMContentLoaded',()=>{
    const userId = JSON.parse(localStorage.getItem('userInfo'));
    if(userId==null){
        window.location.replace("/");
    }
})

const  humbergerIcon = document.getElementById("humberger-icon");
const closeMenuButton = document.getElementsByClassName("close-icon")[0];

const mobileViewNav = document.getElementsByClassName("mobile-nav-view")[0];

const backendMessageHolderForModal = document.getElementById("backend-message");

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

document.getElementById("closeModalBtn").addEventListener("click",()=>{
    document.getElementById("modal").style.display="none";
})

document.getElementsByClassName("add-expenses")[0].addEventListener("click",()=>{
    document.getElementById("modal").style.display="block";
})

/*
* Organising endpoints
* Endpoint to add an expenditure
*Endpoint to update an expense amount
*Endpoint to delete an expense
 */

const formatDate=(date)=>{
    const dateObject = new Date(date);
    const day = dateObject.getDate().toString().padStart(2,'0');
    const month = (dateObject.getMonth()+1).toString().padStart(2,'0');
    const year = dateObject.getFullYear();
    return `${day}/${month}/${year}`;
}
/**
 *  
 * @returns url
 */
const getAddExpenseUrl=()=>{

    const userId = parseInt(JSON.parse(localStorage.getItem('userInfo')).id,10);
    const source = document.getElementById("source-input-area").value;
    const amount = document.getElementById("amount-input-area").value;
    const date = document.getElementById('date').value;
    let dateObj = formatDate(date);

    return `/api/user/expenses?userId=${userId}&&source=${source}&&amount=${amount}&&date=${dateObj}`;

}

/**
 * 
 * @param {number} expenseId 
 * @returns url
 */
const getDeleteExpenseUrl = (expenseId)=>{
    return `/api/user/expenses?expenseId=${expenseId}`;
}

/**
 * 
 * @param {object} param0 
 * @returns 
 */
const getUpdateExpenseAmountUrl = ({expenseId,amount})=>{
    return `/api/user/expenses/update-amount?id=${expenseId}&&amount=${amount}`;
}

/**
 * 
 * @returns url
 */
const getUrlToRetrieveAllExpenses = ()=>{
    const userId = JSON.parse(localStorage.getItem('userInfo')).id;
    return `/api/user/expenses/${userId}`;

}


/*
 * Add interactions to buttons
 * Fetch user Expenses from the server
 * Populate it on a table
 * Include the functionality to update and delete expense
 * Display total expenses at the bottom of the table
 * Add functionality to refresh for new added expenses
 */



/**retrievees all expenses by a given user */
async function getAllUserExpenses(){
    const url = getUrlToRetrieveAllExpenses();
    const res = await fetch(url,{method:'GET'});
    return res;
}

async function deleteExpenseItem(url){
    const res = await fetch(url,{method:"DELETE"});
    const result = res.text()
    alert(result);
}

function updateExpenseAmount(url){
    fetch(url,{method:'PUT'})
    .then(res=>res.text())
    .then(data=>{
        alert(data);
    }).catch(err=>console.log(err));
}

/**
 * 
 * @param {string} url 
 * @returns http response 
 */
async function addNewExpense(url){
    const param = {
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        }
    }
    const res = await fetch(url,param);
    return res;
}

/**adds a new expense  */
document.getElementById('add-btn').addEventListener('click',()=>{
    const url = getAddExpenseUrl();
    const addBtn = document.getElementById("add-btn");
    addBtn.setAttribute("disable",true);
    addBtn.innerText="Adding..."
    addNewExpense(url).then(res=>res.text()).then(data=>{
        backendMessageHolderForModal.innerText = data;

    }).catch(err=>console.log(err)).finally(()=>{
        addBtn.setAttribute("disable",false);
        addBtn.innerText ="Add";
        document.getElementById("source-input-area").value="";
        document.getElementById("amount-input-area").value="";
        document.getElementById("date").value = "";
    })
})


//clears table and replace it with the new data
function clearTable(){
    const table = document.getElementById("table-data");
    while(table.rows.length>1){
        table.deleteRow(1); //delete all rows except the header row
    }
}

function deleteExpenseItem(url){
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
 * @param {number} expenseId 
 * @description generates a modal that allows a user to update amount
 */
function generateModalToUpdateAmount(expenseId){
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
        const url = getUpdateExpenseAmountUrl({expenseId:expenseId,amount:parseFloat(amount)});
        updateExpenseAmount(url);
    })


}


/**
 * 
 * @param {number} expenseId 
 */
function createDeleteModal(expenseId){
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
        const url = getDeleteExpenseUrl(expenseId);
        deleteExpenseItem(url);
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


const populateData = ()=>{
    const table = document.getElementById("table-data");
    getAllUserExpenses().then(res=>{
        if(res.ok){
            return res.json()
        }
    }).then(data=>{
        let totalExpenses = 0;
        if(data.length>0){
            data.map(item=>{
                const{id,amount,source} = item;
                totalExpenses+=amount;
                table.appendChild(tableDataTemplate(id,amount,source));
            })
            document.getElementById('total-income').innerText = parseFloat(totalExpenses).toFixed(2);
        }
    }).catch(err=>console.log(err)).finally(()=>{
        console.log('request was made to server');
    })

}

document.addEventListener("DOMContentLoaded",()=>{
    populateData();
})

document.getElementsByClassName('refresh-for-update')[0].addEventListener('click',()=>{
    clearTable();
    populateData();
})
