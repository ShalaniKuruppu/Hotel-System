import { Component } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin-services/admin.service';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.scss']
})
export class ReservationsComponent {

  currentPage: any = 1;
  total: any;
  reservations :any;

  constructor( private adminService: AdminService,
    private message: NzMessageService
  ) {
    this.getReservations();
  }

  getReservations(){
    this.adminService.getReservations(this.currentPage -1).subscribe(res=>{
      console.log(res);
      this.reservations = res.reservationDtoList;
      this.total = res.totalPages * 1;
    })
  }

  pageIndexChange(value: any){
    this.currentPage = value;
    this.getReservations();
  }

  changeReservationStatus(bookingId: number, status: string){
    this.adminService.changeReservationStatus(bookingId, status).subscribe( res =>{
      this.message.success('Reservation status updated successfully', { nzDuration: 3000 });
      this.getReservations();
    },error=>{
      this.message.error(`${error.error}`,{ nzDuration: 5000});
    });
  }

}
