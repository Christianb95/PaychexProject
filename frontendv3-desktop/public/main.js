const { app, BrowserWindow} = require('electron');
const { ipcMain, dialog } = require("electron");
const fs = require("graceful-fs")
const {resolve} = require("path");
function createWindow () {
    const win = new BrowserWindow({
        width: 800,
        height: 600,
        title: "Paychex App",
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
            enableRemoteModule: true,
            preload: resolve("./public/preload.js")
        }
    })

    // win.setMenuBarVisibility(false)

    win.loadURL('http://localhost:3000')

}

app.whenReady().then(() => {
    createWindow()

    app.on('activate', () => {
        if (BrowserWindow.getAllWindows().length === 0) {
            createWindow()
        }
    })
})

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        app.quit()
    }
})

ipcMain.handle("showDialog", (e, content) => {
    let path = dialog.showSaveDialogSync({
        title: "Save file",
        filters: [{name: "txt", ext: ["txt"]}], // what kind of files do you want to see when this box is opend
        defaultPath: app.getPath("documents") // the default path to save file
    })

    fs.writeFile(path, content, "utf-8", (err) =>{
        if(err){
            console.log(err.message)
        }
    })
})



