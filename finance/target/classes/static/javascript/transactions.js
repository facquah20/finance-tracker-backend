document.addEventListener('DOMContentLoaded',()=>{
    const userId = JSON.parse(localStorage.getItem('userInfo'))
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

document.getElementById("closeModalBtn").addEventListener("click",()=>{
    document.getElementById("modal").style.display="none";
})

document.getElementById('add-transaction').addEventListener('click',()=>{
    document.getElementById('modal').style.display = "block";
})

/*
 *Implement the functionality to record a new transaction
 */

 /**
  * 
  * @param {number} amount 
  * @param {string} personReceived 
  * @param {string} description 
  * @returns url
  */
 const getUrlToAddTransaction = (amount,personReceived,description)=>{
    const userId = JSON.parse(localStorage.getItem("userInfo")).id;
    return `/api/user/transaction?userId=${userId}&&amount=${amount}&&personReceived=${personReceived}&&description=${description}`;
 }

 async function addTransaction(url){
    const res = await fetch(url,{method:'POST'});
    return res;
 }

 const initiateAddTransactionRequest = ()=>{
    const receiver = document.getElementById("source-input-area");
    const amount = document.getElementById("amount-input-area");
    const description = document.getElementById("transaction-description");
    const addBtn = document.getElementById("add-btn");
    addBtn.innerText="Adding..."
    const url = getUrlToAddTransaction(amount.value,receiver.value,description.value);
    addTransaction(url)
    .then(res=>res.text())
    .then(data=>{
        const message = document.getElementById("backend-message");
        message.innerText = data;
    }).catch(err=>console.log(err)).finally(()=>{
        receiver.value ="";
        amount.value="";
        description.value="";
        document.getElementById("backend-message").value="";
        console.log("adding transaction done");
        addBtn.innerText = "Add";

    })
 }

 document.getElementById("add-btn").addEventListener('click',initiateAddTransactionRequest);


 /*
  * Implementing how al user transactions will be rendered on a table
  */

 const getUrlToDisplayAllTransactions = ()=>{
    const userId = JSON.parse(localStorage.getItem('userInfo')).id;
    return `/api/user/transaction?userId=${userId}`;
 }

 function clearTable(){
    const table = document.getElementById("table-data");
    while(table.rows.length>1){
        table.deleteRow(1); //delete all rows except the header row
    }
}
 const tableDataTemplate = (id,amount,receiver,description)=>{
    const row = document.createElement("tr");
    const idTd = document.createElement('td');
    const sourceTd = document.createElement('td');
    const amountTd = document.createElement('td');
    const descriptionTd = document.createElement('td');

    const img1 = document.createElement('img');
    const img2 = document.createElement('img');
    const imagesTd = document.createElement('td');

    img1.addEventListener('click', ()=>{
        //calls to create a delete modal alert
        createDeleteModalScreen(id);
    })

    img2.addEventListener('click',()=>{
        generateModalToUpdateTransactionAmount({transactionId:id});
    })

    //setting the values for each
    idTd.textContent = id;
    sourceTd.textContent =receiver;
    amountTd.textContent =amount;
    descriptionTd.textContent = description;
    
    img1.src="/images/delete-icon.jpg"
    img1.width=40;
    img1.height=40;
    img2.src="/images/edit-icon.png"
    img2.width=40;
    img2.height=40;

    imagesTd.append(img1,img2);
    row.append(idTd,amountTd,sourceTd,descriptionTd,imagesTd);
    return row;
 }

 async function fetchAllTransactions(url){
    const res = await fetch(url);
    return res;
 }
 const populateData = ()=>{
    const url = getUrlToDisplayAllTransactions();

    fetchAllTransactions(url).then(res=>res.json())
    .then(data=>{
        let totalTransactions= 0;
        const table = document.getElementById('table-data');
        if(data.length>0){
            
            data.map(transaction=>{
                const{id,amount,description,personReceived}= transaction;
                table.appendChild(tableDataTemplate(id,amount,personReceived,description));
                if(typeof amount == 'number'){
                    totalTransactions+=amount;
                }
                
            })
           document.getElementById('total-income').innerText=parseFloat(totalTransactions).toFixed(2); 
        }
    })
    
 }

 document.addEventListener('DOMContentLoaded',populateData)
 document.getElementsByClassName("refresh-for-update")[0].addEventListener("click",()=>{
    clearTable();
    populateData();
 })


 /*
  * Implementing deletion and updating 
  */

 const getDeleteTransactionUrl = (transactionId)=>{
    return `/api/user/transaction?transactionId=${transactionId}`;

 }
 const deleteTransactionItem = (url)=>{
    fetch(url,{method:"DELETE"})
    .then(res=>res.text())
    .then(result =>alert(result)).catch(err=>console.log(err));
 }

 /**
  * 
  * @param {number} transactionId 
  */
 const createDeleteModalScreen = (transactionId)=>{
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
        const url = getDeleteTransactionUrl(transactionId);
        deleteTransactionItem(url);
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

 const getUrlToUpdateTransactionAmount = (transactionId,amount)=>{
    return `/api/user/transaction/update-amount?transactionId=${transactionId}&&amount=${amount}`;
 }
 const generateModalToUpdateTransactionAmount = ({transactionId})=>{
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
        const url = getUrlToUpdateTransactionAmount(transactionId,parseFloat(amount));
        updateTransactionAmount(url);
    })

 }
 function updateTransactionAmount(url){
    fetch(url,{method:'PUT'})
    .then(res=>res.text())
    .then(data=>{
        alert(data);
    }).catch(err=>console.log(err));
}