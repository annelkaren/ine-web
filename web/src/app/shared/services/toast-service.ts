import { Injectable } from "@angular/core";
import { MessageService } from "primeng/api";

@Injectable()
export class ToastService {

    constructor(
        private messageService: MessageService
    ) { }

    public showErrorToast(message: string): void {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: message, life: 8000 });
    }

    public showSuccessToast(message: string): void {
        this.messageService.add({ severity: 'success', summary: 'Éxito', detail: message, life: 8000 });
    }

    public showWarnToast(message: string): void {
        this.messageService.add({ severity: 'warn', summary: 'Advertencia', detail: message, life: 8000 });
    }

    public showToast(type: number, message: string): void {
        if (type === 1) {
            this.showSuccessToast(message);
        } else if (type === 3) {
            this.showWarnToast(message);
        } else {
            this.showErrorToast(message);
        }
    }
}