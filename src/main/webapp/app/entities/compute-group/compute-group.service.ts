import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IComputeGroup } from 'app/shared/model/compute-group.model';

type EntityResponseType = HttpResponse<IComputeGroup>;
type EntityArrayResponseType = HttpResponse<IComputeGroup[]>;

@Injectable({ providedIn: 'root' })
export class ComputeGroupService {
  public resourceUrl = SERVER_API_URL + 'api/compute-groups';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/compute-groups';

  constructor(protected http: HttpClient) {}

  create(computeGroup: IComputeGroup): Observable<EntityResponseType> {
    return this.http.post<IComputeGroup>(this.resourceUrl, computeGroup, { observe: 'response' });
  }

  update(computeGroup: IComputeGroup): Observable<EntityResponseType> {
    return this.http.put<IComputeGroup>(this.resourceUrl, computeGroup, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IComputeGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComputeGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComputeGroup[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
