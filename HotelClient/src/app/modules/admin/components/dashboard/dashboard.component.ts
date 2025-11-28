import { Component } from '@angular/core';
import { AdminService } from '../../admin-services/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  currentPage = 1;
  rooms = [];
  total:any;
  loading = false;

  constructor(private adminService: AdminService, 
    private message: NzMessageService,
    private modalService: NzModalService
  ) {
    this.getRooms();
   }

  getRooms() {
    this.adminService.getRooms(this.currentPage - 1).subscribe(res=>{
      console.log(res);
      this.rooms = res.roomDtoList;
      this.total = res.totalPages * 1;
    })
  }

  pageIndexChange(value:any){
    this.currentPage = value;
    this.getRooms();
  }

  showConfirm(roomId: number): void {
    this.modalService.confirm({
      nzTitle: 'Confirm',
      nzContent: 'Are you sure you want to delete this room?',
      nzOkText: 'Delete',
      nzCancelText: 'Cancel',
      nzOnOk: () => this.deleteRoom(roomId)
    })
  }


  deleteRoom(roomId: number){
    this.adminService.deleteRoom(roomId).subscribe( res =>{
      this.message.success('Room deleted successfully', { nzDuration: 3000 });
      this.getRooms();
    },error=>{
      this.message.error(`${error.error}`,{ nzDuration: 5000});
    });
  }

}
