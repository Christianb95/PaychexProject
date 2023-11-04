const{app, BrowserWindow} = require("electron");
const {join} = require("path");
const { ipcMain, dialog } = require('electron');

require('@electron/remote/main').initialize()
function createWindow () {
    const win = new BrowserWindow({
        width: 800,
        height: 600,
        title: "Paychex App",
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
            preload: join(__dirname, 'preload.js')
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

ipcMain.on('save-dialog', (event, options) => {
    dialog.showSaveDialogSync(options, (filePath) => {
        event.reply('dialog-response', filePath);
    });
});