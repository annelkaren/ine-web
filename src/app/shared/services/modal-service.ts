import { Injectable } from "@angular/core";
import {
    DialogService, DynamicDialogRef,
} from "primeng/dynamicdialog";
import { AddComponent } from "src/app/pages/shared/modals/add/add.component";

@Injectable()
export class ModalService {

    constructor(
        private dialog: DialogService
    ) { }

    public add(header: string): DynamicDialogRef {
        return this.dialog.open(AddComponent, {
            header: header,
            width: '40%',
        });
    }
}