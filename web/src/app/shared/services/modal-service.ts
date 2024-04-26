import { Injectable } from "@angular/core";
import {
    DialogService, DynamicDialogRef,
} from "primeng/dynamicdialog";
import { AddComponent } from "src/app/pages/shared/modals/add/add.component";
import { NoValidComponent } from "src/app/pages/shared/modals/no-valid/no-valid.component";
import { ValidateComponent } from "src/app/pages/shared/modals/validate/validate.component";

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

    public noValid(): DynamicDialogRef {
        return this.dialog.open(NoValidComponent, {
            header: "Capturas no coinciden",
            width: '40%',
        });
    }

    public valid(): DynamicDialogRef {
        return this.dialog.open(ValidateComponent, {
            header: "Validar formulario",
            width: '40%',
        });
    }
}