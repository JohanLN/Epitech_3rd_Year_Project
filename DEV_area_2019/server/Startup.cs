using Area.Scheduler;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System;

namespace Area
{
    public class Startup
    {
        private static readonly string CORS = "AllowAllOrigins";
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddCors((opt) =>
            {
                opt.AddPolicy(CORS, (builder) =>
                {
                    string[] ORIGINS = { "http://localhost:8081", "https://localhost:8081" };

                    builder.WithOrigins(ORIGINS).AllowAnyMethod().AllowAnyHeader().AllowCredentials();
                });
            });
            services.AddDistributedMemoryCache();
            services.AddSession((opt) =>
            {
                opt.IdleTimeout = TimeSpan.FromMinutes(10);
                opt.Cookie.SameSite = SameSiteMode.None;
                opt.Cookie.HttpOnly = false;
                opt.Cookie.IsEssential = true;
            });
            services.AddAuthentication().AddFacebook((opt) =>
            {
                opt.AppId = Configuration["Authentification:Facebook:AppId"];
                opt.AppSecret = Configuration["Authentification:Facebook:AppSecret"];
            });
            services.AddControllers();
            services.AddSingleton<IScheduledTask, RefreshApiData>();
            services.AddScheduler((sender, args) =>
            {
                Console.WriteLine(args.Exception.Message);
                args.SetObserved();
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            app.UseHttpsRedirection();
            app.UseRouting();
            app.UseSession();
            app.UseCors(CORS);
            app.UseAuthorization();
            app.UseEndpoints(endpoints =>
            {
                if (env.IsDevelopment())
                {
                    endpoints.MapControllers().RequireCors(CORS);
                }
                else
                {
                    endpoints.MapControllers();
                }
            });
        }
    }
}
